package com.android.app_aqi

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.work.*
import com.android.app_aqi.room.AqiDatabase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.util.concurrent.TimeUnit

lateinit var aqiDb: AqiDatabase
lateinit var workManager: WorkManager

class MyApplication : Application(), Configuration.Provider{

    companion object {
        lateinit var INSTANCE: MyApplication

    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        aqiDb = AqiDatabase.getInstance(this)

        INSTANCE = this

        val constraint: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workRequest = PeriodicWorkRequest.Builder(UpdateAQIWorker::class.java,15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(Constant.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)


        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Log.d(TAG, token)
            })
    }

    override fun getWorkManagerConfiguration(): Configuration
            = Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()

}