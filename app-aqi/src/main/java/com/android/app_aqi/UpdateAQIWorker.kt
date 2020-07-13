package com.android.app_aqi

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.app_aqi.api.TaskManager
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.room.AqiDatabase
import com.android.app_aqi.task.AqiDataTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class UpdateAQIWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private lateinit var aqiDataTask: AqiDataTask

    override fun doWork(): Result {

        aqiDataTask = AqiDataTask(object : AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {

                val aqiDatabase = Room.databaseBuilder(applicationContext, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                GlobalScope.launch {
                    if(aqiDatabase.getAqiDao().getSiteList().isNullOrEmpty()){
                        Log.d("esther", "Database is Null Or Empty")
                        result!!.forEach {aqi -> //insertAqi(aqi)
                            Log.d("esther", "insert! : " +aqi.siteName +"  "+ aqi.publishTime.toString() + " " +"AQI ${aqi.aQI}")
                            if(aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) return@forEach
                            val siteId = aqi.siteId.toLong()
                            val publishTime = DateUtil.DateToStamp(aqi.publishTime)
                            aqi.id = siteId + publishTime
                            aqiDatabase.getAqiDao().insert(aqi)
                        }

                    } else {
                        result!!.forEach {aqi ->
                            if(aqiDatabase.getAqiDao().filterBySite(aqi.siteId, aqi.publishTime).isNotEmpty()) return@forEach
                            Log.d("esther", "insert! : " +aqi.siteName +"  "+ aqi.publishTime.toString() + " " +"AQI ${aqi.aQI}")
                            if(aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) return@forEach
                            val siteId = aqi.siteId.toLong()
                            val publishTime = DateUtil.DateToStamp(aqi.publishTime)
                            aqi.id = siteId + publishTime
                            aqiDatabase.getAqiDao().insert(aqi)
                        }
                    }
                    aqiDatabase.close()
                    Log.d("esther", "Finish! ")
                }

            }

//            private fun insertAqi(aqi: AqiModel) {
//
//            }


            override fun onFailed(error: ResponseBody?) {
                Log.d("esther", "onFailed! " + error.toString())
                TaskManager(aqiDataTask).start()
            }

        } , RequestEntity(Constant.top, 0, "json", ""))

        TaskManager(aqiDataTask).start()


        return Result.success()
    }
}
