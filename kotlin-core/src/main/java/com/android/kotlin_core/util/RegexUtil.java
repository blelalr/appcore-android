package com.android.kotlin_core.util;

import java.util.Arrays;
import java.util.Calendar;

/****************************************************************
 * Copyright (C) Cloud Interactive Corporation. All rights reserved.
 *
 * Author: nathan
 * Create Date: 2018/10/21
 * Usage:
 *
 * Revision History
 * Date         Author      Description
 **************************************************************/
public class RegexUtil {

    /**
     * 檢查密碼
     * 密碼長度必須有八碼，並且包含至少一個小寫字母與一個大寫字母和一個數字 "^(?=^.{8,20}$)((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]))^.*$"
     * 數字及英文字母，最少6位，最多12位："^([a-zA-Z0-9]){6,12}$"
     * 任意符號，最少6位，最多12位："^(.|\\r|\\n){6,12}$"
     **/
    public static boolean checkPassword(String password) {
        return password.matches("^(?=^.{8,20}$)((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]))^.*$");
    }

    /**
     * 檢查Email
     **/
    public static boolean checkEmail(String email) {
        return email.matches("^([\\w-.]+)@(([\\w-]+\\.)+)([a-zA-Z]{2,4})$");
    }

    /**
     * 檢查手機號碼
     **/
    public static boolean checkCellPhoneNumber(String phone) {
        return phone.matches("^09([0-9]{8})$");
    }

    /**
     * 檢查身分證字號
     **/
    public static boolean checkIdentificationNumber(String id) {
        if (id.matches("^([A-Z])([1|2])([0-9]{8})$")) {
            id = id.toUpperCase();
            int checkCode = 0;
            int firstLetterNum = convertNumberFromLetter(id.charAt(0));
            for (int i = 1; i < 9; i++) {
                checkCode = checkCode + (Integer.parseInt(String.valueOf((id.charAt(i)))) * (9 - i));
            }
            int result = firstLetterNum + checkCode + Integer.parseInt(String.valueOf((id.charAt(9))));
            return result % 10 == 0;
        } else {
            return false;
        }
    }

    /**
     * 檢查居留證號
     **/
    public static boolean checkUnifiedCertificateNumber(String id) {
        int verifyNum = 0;

        final char[] pidCharArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        // 原居留證第一碼英文字應轉換為10~33，十位數*1，個位數*9，這裡直接作[(十位數*1) mod 10] + [(個位數*9) mod 10]
        final int[] pidResidentFirstInt = {1, 10, 9, 8, 7, 6, 5, 4, 9, 3, 2, 2, 11, 10, 8, 9, 8, 7, 6, 5, 4, 3, 11, 3, 12, 10};
        // 原居留證第二碼英文字應轉換為10~33，並僅取個位數*6，這裡直接取[(個位數*6) mod 10]
        final int[] pidResidentSecondInt = {0, 8, 6, 4, 2, 0, 8, 6, 2, 4, 2, 0, 8, 6, 0, 4, 2, 0, 8, 6, 4, 2, 6, 0, 8, 4};

        if (id.matches("[A-Z]{1}[A-D]{1}[0-9]{8}")) {
            id = id.toUpperCase();

            verifyNum += pidResidentFirstInt[Arrays.binarySearch(pidCharArray, id.charAt(0))];
            verifyNum += pidResidentSecondInt[Arrays.binarySearch(pidCharArray, id.charAt(1))];

            for (int i = 2, j = 7; i < 9; i++, j--) {
                verifyNum += Character.digit(id.charAt(i), 10) * j;
            }

            verifyNum = (10 - (verifyNum % 10)) % 10;
            return verifyNum == Character.digit(id.charAt(9), 10);
        } else {
            return false;
        }
    }

    /**
     * 檢查中文字
     **/
    public static boolean checkChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            String strChar = str.substring(i, i + 1);
            if (strChar.matches("[\\u4E00-\\u9FA5]+")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 檢查英文字
     **/
    public static boolean checkEnglishChar(String str) {
        return str.matches("^(([0-9])*([a-zA-Z])+( )*([0-9])*)+$");
    }

    /**
     * 手機條碼載具
     **/
    public static boolean checkInvoiceMobileDevice(String invoiceMobileDevice) {
        return invoiceMobileDevice.matches("^/([a-zA-Z0-9.+-]{7})$");
    }

    /**
     * 自然人憑證
     **/
    public static boolean checkCitizenDigitalCertificate(String citizenDigitalCertificate) {
        return citizenDigitalCertificate.matches("^([a-zA-Z]{2})([0-9]{14})$");
    }

    /**
     * 統一編號
     **/
    public static boolean checkCompanyId(String companyId) {
        return companyId.matches("^([0-9]{8})$");
//        int checkCode = 0;
//        int checkCode1 = 0;
//        int[] checkFormat = new int[]{1, 2, 1, 2, 1, 2, 4, 1};
//        for (int i = 0; i < 8; i++) {
//            if (i == 7) {
//                int operator = (Integer.parseInt(String.valueOf((companyId.charAt(i)))) * checkFormat[i]) / 10 + (Integer.parseInt(String.valueOf((companyId.charAt(i)))) * checkFormat[i]) % 10;
//                if (operator > 10) {
//                    checkCode1 = checkCode + 1;
//                } else {
//                    checkCode = checkCode + operator;
//                }
//            } else {
//                int operator = (Integer.parseInt(String.valueOf((companyId.charAt(i)))) * checkFormat[i]) / 10 + (Integer.parseInt(String.valueOf((companyId.charAt(i)))) * checkFormat[i]) % 10;
//                checkCode = checkCode + operator;
//            }
//        }
//        return checkCode % 10 == 0 || (checkCode != 0 && checkCode1 % 10 == 0);
    }

    /**
     * 檢查網站
     **/
    public static boolean checkHtml(String html) {
        return html.matches("^(http[s]?://(((.|\\r|\\n)+\\.)+)((.|\\r|\\n)+)[/]?)$");
    }

    private static int convertNumberFromLetter(Character Letter) {
        if (Letter.equals('B') || Letter.equals('N') || Letter.equals('Z')) {
            return 0;
        } else if (Letter.equals('A') || Letter.equals('M') || Letter.equals('W')) {
            return 1;
        } else if (Letter.equals('K') || Letter.equals('L') || Letter.equals('Y')) {
            return 2;
        } else if (Letter.equals('J') || Letter.equals('V') || Letter.equals('X')) {
            return 3;
        } else if (Letter.equals('H') || Letter.equals('U')) {
            return 4;
        } else if (Letter.equals('G') || Letter.equals('T')) {
            return 5;
        } else if (Letter.equals('F') || Letter.equals('S')) {
            return 6;
        } else if (Letter.equals('E') || Letter.equals('R')) {
            return 7;
        } else if (Letter.equals('D') || Letter.equals('O') || Letter.equals('Q')) {
            return 8;
        } else if (Letter.equals('C') || Letter.equals('I') || Letter.equals('P')) {
            return 9;
        } else {
            return -1;
        }
    }

    public static boolean checkChName(String chName) {
        if (chName.matches("[0-9]"))
            return false;
        else
            return chName.matches("^(([0-9])*([a-zA-Z\\u4E00-\\u9FA5])+( )*([0-9])*)+$");
    }

    public static boolean checkIsAgeOver16(String birthday) {
        if (getAge(birthday) >= 16) return true;
        else return false;
    }

    private static int getAge(String birthday) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = (cal.get(Calendar.MONTH)) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int birthYear = Integer.valueOf(birthday.split("-")[0]);
        int birthMonth = Integer.valueOf(birthday.split("-")[1]);
        int birthDay = Integer.valueOf(birthday.split("-")[2]);
        int age = 0;
        age = currentYear - birthYear;
        if (birthMonth > currentMonth) {
            age = age - 1;
        } else if (birthMonth == currentMonth) {
            if (birthDay > currentDay) {
                age = age - 1;
            }
        }
        return age;
    }

    /**
     * 家樂福企業卡號
     **/
    public static boolean checkCompanyVipNumber(String companyVipNumber) {
        return companyVipNumber.matches("^(739[1|2|3|4]000)([0-9]{9})$");
    }
}
