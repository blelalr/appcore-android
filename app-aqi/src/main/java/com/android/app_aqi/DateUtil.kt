package com.android.app_aqi

import android.text.format.DateUtils
import java.text.SimpleDateFormat

object DateUtil {

    fun isToday(monitorDate: String) : Boolean {
        //2020/6/11 上午 10:00:00

        var monitorDateTime = SimpleDateFormat("yyyy/MM/dd").parse(monitorDate)


        return DateUtils.isToday(monitorDateTime.time)
    }

}