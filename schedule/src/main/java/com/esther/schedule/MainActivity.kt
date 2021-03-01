package com.esther.schedule

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private val calenderViewModel : CalenderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calenderViewModel.initCalendar()
        initView()
    }

    private fun initView() {
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
        btnNext.setOnClickListener(this)
        btnPrev.setOnClickListener(this)
        calenderViewModel.getStartDayOfThisWeek().observe(this, Observer {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container, WeekFragment.newInstance("", "")).commit()
        })

    }


    override fun onClick(view: View) {
        if(view.id == R.id.btn_next){
            calenderViewModel.toNextWeek()
        }

        if(view.id == R.id.btn_prev){
            calenderViewModel.toPrevWeek()
        }
    }

}