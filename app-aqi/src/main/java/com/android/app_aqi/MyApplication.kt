package com.android.app_aqi

import android.R
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.work.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
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