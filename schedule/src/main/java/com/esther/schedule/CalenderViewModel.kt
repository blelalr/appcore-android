package com.esther.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CalenderViewModel :ViewModel() {
    private val calendar = Calendar.getInstance()
    private var startDayOfThisWeek = MutableLiveData<Date>()

    fun initCalendar() {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)

        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        Log.d("esther","Start of this week:       " + calendar.time)
//        Log.d("esther","MONTH:       " + months[calendar.get(Calendar.MONTH)])
//        Log.d("esther","DATE:       " + calendar.get(Calendar.DAY_OF_MONTH))
//        Log.d("esther","... in milliseconds:      " + calendar.timeInMillis)

        startDayOfThisWeek.value = calendar.time
//        calendar.add(Calendar.WEEK_OF_YEAR, 1) //Find next Week
//        Log.d("esther","Start of next week:       " + calendar.time)
//        Log.d("esther","... in milliseconds:      " + calendar.timeInMillis)
    }

    fun toNextWeek() {
        startDayOfThisWeek.value = calendar.time
        Log.d("esther","Start of this week:       " + calendar.time)
    }

    fun toPrevWeek() {
        calendar.add(Calendar.WEEK_OF_YEAR, -2)
        startDayOfThisWeek.value = calendar.time
        Log.d("esther","Start of this week:       " + calendar.time)
    }

    fun getStartDayOfThisWeek(): MutableLiveData<Date> {
        return startDayOfThisWeek
    }

    fun getCalender(): Calendar {
        return calendar
    }
}