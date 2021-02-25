package com.android.codingtest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class HelloJava {

    public static void main(String[] args) {
        int[] n = {3,3,7,8,2,4};
        int target = 7;
        System.out.println(Arrays.toString(twoSum(n, target)));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[2];
        for(int index = 0; index < nums.length; index ++) {
            int pair = target - nums[index];
            if(map.containsKey(pair)){
                ans = new int[]{index, map.get(pair)};
            } else {
                map.put(nums[index], index);
            }
        }

        return ans;
    }


    public boolean isPalindrome(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        int j = chars.length -1;
        while(i<=j) {
            if (!Character.isLetterOrDigit(chars[i])) {
                i++;
            } else if (!Character.isLetterOrDigit(chars[j])) {
                j--;
            } else{
                if(Character.toLowerCase(chars[i]) !=
                        Character.toLowerCase(chars[j]))
                    return false;
                i++;
                j--;
            }
        }
        return true;
    }
}
