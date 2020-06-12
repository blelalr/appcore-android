package com.android.app_aqi

import android.os.AsyncTask
import android.os.Bundle
import android.text.format.DateUtils
import android.text.format.DateUtils.isToday
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.app_aqi.task.AqiDataTask
import com.android.app_aqi.api.TaskManager
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.room.AqiDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MainActivity : AppCompatActivity() {

    private lateinit var aqiDataTask: AqiDataTask


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        aqiDataTask = AqiDataTask(object : AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {
                GlobalScope.launch {
                    val todoDatabase = Room.databaseBuilder(applicationContext, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build()
                    result!!.forEach { aqi ->
                        when {
                            DateUtil.isToday(aqi.monitorDate!!) -> todoDatabase.getAqiDao().insert(aqi)
//                            !DateUtil.isToday(aqi.monitorDate!!) ->  {
//                                Log.d("esther", "finish!")
//                                return@launch
//                            }
                        }
                    }
                    loadMore()
                }
            }

            override fun onFailed(error: ResponseBody?) {

                Log.d("esther", "onFailed! " + error.toString())
            }

        } ,"ATM00488", RequestEntity(Constant.top, 0, "json", "MonitorDate"))

        TaskManager(aqiDataTask).start()


    }

    private fun loadMore() {
        aqiDataTask.request.skip = aqiDataTask.request.skip?.plus(Constant.top)
        aqiDataTask.params = aqiDataTask.genParams(aqiDataTask.request)
        TaskManager(aqiDataTask).start()
        Log.d("esther", "load More!")
    }

}
