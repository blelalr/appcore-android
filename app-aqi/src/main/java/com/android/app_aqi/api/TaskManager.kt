package com.android.app_aqi.api

import android.util.Log
import com.android.app_aqi.api.ApiServiceBuilder.Companion.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskManager {

    private var taskService: TaskService

    constructor(taskService: TaskService) {
        this.taskService = taskService
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("esther", "" + taskService.params.toString())
            val response = retrofit.getAqi(taskService.siteId, taskService.params)

            withContext(Dispatchers.Main) {
                // To get BookStore Object
                if (response.isSuccessful) {

                    taskService.onTaskSucceed(response.body()?.get(0))

                } else {

                    taskService.onTaskFailed(response.errorBody())

                    // Handle errors here
                }
            }


        }
    }
}