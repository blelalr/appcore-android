package com.android.app_aqi.task

import android.util.Log
import com.android.app_aqi.RequestEntity
import com.android.app_aqi.api.TaskService
import com.android.app_aqi.model.BaseResponse
import okhttp3.ResponseBody

class AqiDataTask : TaskService {

    constructor(siteId: String, request: RequestEntity) {
        this.siteId = siteId
        this.params = genParams(request)

    }
    override fun onTaskSucceed(entity: BaseResponse?) {
        Log.d("esther", entity.toString())

    }

    override fun onTaskFailed(errorBody: ResponseBody?) {

    }

}