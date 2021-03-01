package com.android.app_aqi

import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun isToday(monitorDate: String) : Boolean {
        //2020/6/11 上午 10:00:00

        var monitorDateTime = SimpleDateFormat("yyyy/MM/dd").parse(monitorDate)


        return DateUtils.isToday(monitorDateTime.time)
    }

    fun covertDateToTimestamp(date: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.TAIWAN)

        return simpleDateFormat.parse(date).time
    }
}