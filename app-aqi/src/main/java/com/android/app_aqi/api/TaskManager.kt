package com.android.app_aqi.api

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskManager {
    private var apiServiceBuilder = ApiServiceBuilder()
    private var taskService: TaskService

    constructor(taskService: TaskService) {
        this.taskService = taskService
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiServiceBuilder.get(taskService.params)
            // To get aqi response object
            if (response.isSuccessful) {
                Log.d("esther", response.body().toString())
                taskService.onTaskSucceed(response.body())
            // Handle errors here
            } else {
                Log.d("esther", response.errorBody().toString())
                taskService.onTaskFailed(response.errorBody())
            }
        }
    }
}