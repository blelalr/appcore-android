package com.android.app_aqi

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.app_aqi.api.TaskManager
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.repository.AqiRepositoryImpl
import com.android.app_aqi.room.AqiDatabase
import com.android.app_aqi.task.AqiDataTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class UpdateAQIWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private var repositoryImpl = AqiRepositoryImpl()

    override fun doWork(): Result {
        repositoryImpl.insertAqiData()
        return Result.success()
    }
}
