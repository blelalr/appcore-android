package com.android.app_aqi

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.app_aqi.task.AqiDataTask
import com.android.app_aqi.api.TaskManager
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.room.AqiDatabase
import okhttp3.ResponseBody

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDataBase()

        TaskManager(AqiDataTask(object : AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {

                AsyncTask.execute {
                    val todoDatabase = Room.databaseBuilder(applicationContext, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build()
                    for (aqi in result!!) {
                        todoDatabase.getAqiDao().insert(aqi)
                    }
                    for ( item in todoDatabase.getAqiDao().getAll()) {

                        Log.d("esther", item.siteName.toString())
                    }
                }

            }

            override fun onFailed(error: ResponseBody?) {

            }

        } ,"ATM00488", RequestEntity(20, 0, "json"))).start()


    }

    private fun setUpDataBase() {
        if(applicationContext == null) return


    }
}
