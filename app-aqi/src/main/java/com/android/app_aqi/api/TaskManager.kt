package com.android.app_aqi.api

class TaskManager {
    private var apiServiceBuilder = ApiServiceBuilder()
    private var taskService: TaskService

    constructor(taskService: TaskService) {
        this.taskService = taskService
    }

    fun start() {
        val response = apiServiceBuilder.get(taskService.params)
        // To get aqi response object
        if (response.isSuccessful) {
            taskService.onTaskSucceed(response.body())

        // Handle errors here
        } else {
            taskService.onTaskFailed(response.errorBody())
        }
    }
}