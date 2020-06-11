package com.android.app_aqi.task

import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.api.TaskService
import com.android.app_aqi.model.AqiModel
import okhttp3.ResponseBody

class AqiDataTask : TaskService{

    interface TaskListener {
        fun  onSucceed(result: List<AqiModel>?)
        fun onFailed(error: ResponseBody?)
    }

    var taskListener : TaskListener

    constructor(listener: TaskListener, siteId: String, request: RequestEntity) {
        this.taskListener = listener
        this.siteId = siteId
        this.params = genParams(request)
    }

    override fun onTaskSucceed(result: Any?) {
        if(result != null) {
            var response : List<AqiModel> = result.convert()
            taskListener.onSucceed(response)
        } else {
            taskListener.onFailed(result)
        }
    }

    override fun onTaskFailed(error: Any?) {
        var response : ResponseBody = error.convert()
        taskListener.onFailed(response)
    }

}