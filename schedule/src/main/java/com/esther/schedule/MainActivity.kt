package com.esther.schedule

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var llWeek: LinearLayout
    private val weekDays = arrayListOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        llWeek = findViewById(R.id.ll_week_view_root)
        fragmentTransaction = supportFragmentManager.beginTransaction()
        for(index in 1 until weekDays.size +1){
            addScheduleFragment(index)
        }
        fragmentTransaction.commit()
    }

    private fun addScheduleFragment(index: Int) {
        val frame = FrameLayout(this)
        val frameParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        frameParams.weight = 1f
        frame.layoutParams = frameParams
        frame.id = index
        val fragment = ScheduleFragment.newInstance(weekDay = weekDays[index-1])
        fragmentTransaction.add(index, fragment)
        llWeek.addView(frame)
    }
}