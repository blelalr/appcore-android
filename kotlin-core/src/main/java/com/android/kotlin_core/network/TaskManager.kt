package com.android.kotlin_core.network

import com.android.kotlin_core.util.AppConst
import com.android.kotlin_core.util.Data
import com.android.kotlin_core.util.DebugLog
import com.android.kotlin_core.util.Util
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TaskManager<PostData, ResponseData> constructor(var taskService:TaskService<PostData, ResponseData>){
    private var apiService = ApiServiceBuilder().apiService

    suspend fun startTask() {
        withContext(Dispatchers.IO) {
            var response: Response<Any>
            when (taskService.getMethod()) {
                AppConst.HttpMethodType.GET -> {
                    response = apiService.get(taskService.getEndPoint(), taskService.genPostData())
                    if (response.isSuccessful) {
                        DebugLog.d(response.body().toString())

                        val data: ResponseData = Util.jsonToObject(response.body().toString(), object : TypeToken<ResponseData?>() {}.type)

                        taskService.onTaskSucceed(data)
                    } else {
                        DebugLog.d(response.errorBody().toString())
                        taskService.onTaskFailed(response.errorBody().toString())
                    }

                }
                AppConst.HttpMethodType.POST -> {
                    response = apiService.post(taskService.getEndPoint(), taskService.postData)
                }

            }

        }
    }
}