package com.android.app_aqi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.app_aqi.task.AqiDataTask
import com.android.app_aqi.api.TaskManager

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TaskManager(AqiDataTask("ATM00679", RequestEntity(0, 10, "json"))).start()
    }
}
