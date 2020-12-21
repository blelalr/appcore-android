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
                response.body()?.let {
                    taskService.onTaskSucceed(it)
                    Log.d("esther", it.toString())
                }
            // Handle errors here
            } else {
                response.errorBody()?.let {
                    taskService.onTaskFailed(it)
                }
            }
        }

    }
}