package com.android.app_aqi.api

import com.android.app_aqi.api.ApiServiceBuilder.Companion.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskManager {

    private var taskService: TaskService

    constructor(taskService: TaskService) {
        this.taskService = taskService
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            var response = retrofit.getAqi(taskService.params)
            // To get aqi response object
            if (response.isSuccessful) {
                taskService.onTaskSucceed(response.body())

            // Handle errors here
            } else {
                taskService.onTaskFailed(response.errorBody())
            }
        }
    }
}