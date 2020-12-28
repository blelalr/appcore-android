package com.android.kotlin_core.network

import com.android.kotlin_core.util.AppConst
import com.android.kotlin_core.util.DebugLog
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class TaskManager<PostData, ResponseData> constructor(var taskService: TaskService<PostData, ResponseData>) {
    private var apiService = ApiServiceBuilder().apiService

    suspend fun startTask(): ApiResult<ResponseData> {
        return withContext(Dispatchers.IO) {
            DebugLog.e("Task Start   : [Method] -- ${taskService.getMethod()}")
            val result = when (taskService.getMethod()) {
                AppConst.HttpMethodType.GET -> {
                    if (taskService.requestData != null) {
                        apiService.get(taskService.getEndPoint(), taskService.genQueryMap())
                    } else {
                        apiService.get(taskService.getEndPoint())
                    }
                }
                AppConst.HttpMethodType.POST -> {
                    apiService.post(taskService.getEndPoint(), taskService.requestData)
                }
            }

            DebugLog.e("Task Start   : [ Url ] -- ${ApiConstant.BASE_URL}${taskService.getEndPoint()}")
            taskService.requestData?.let { DebugLog.e("Task Start   : [Request Data] -- ${taskService.requestData}") }

            return@withContext try {
                if (result.isSuccessful) {
                    taskService.responseBody = result.body()
                    val resObj = taskService.genResponse()
                    DebugLog.e("Task Response: [Success]\n${Gson().toJson(resObj)}")
                    ApiResult.Success(resObj)
                } else {
                    DebugLog.e("Task Response: [Error] -- Code: ${result.code()}")
                    DebugLog.e("Task Response: [Error] -- Message: ${result.message()}")
                    ApiResult.Error(result.code(), result.message())
                }
            } catch (e: Exception) {
                DebugLog.e("Task Response: [Exception] -- $e")
                ApiResult.Exception(e)
            }

        }
    }
}