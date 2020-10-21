package com.android.app_aqi

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.app_aqi.repository.AqiRepositoryImpl

class UpdateAQIWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var repositoryImpl = AqiRepositoryImpl()

    override fun doWork(): Result {
        repositoryImpl.insertAqiData()
        return Result.success()
    }
}
