package com.android.app_aqi

import android.app.Application
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit


class MyApplication : Application(), Configuration.Provider{

    override fun onCreate() {
        super.onCreate()
        val constraint: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workRequest = PeriodicWorkRequest.Builder(UpdateAQIWorker::class.java,15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        val workManager = WorkManager.getInstance(this)


        workManager.enqueueUniquePeriodicWork(Constant.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration
            = Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()

}