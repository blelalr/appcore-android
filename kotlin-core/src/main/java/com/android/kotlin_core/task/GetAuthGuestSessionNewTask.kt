package com.android.kotlin_core.task
import com.android.kotlin_core.model.GetAuthGuestSessionNewRequestData
import com.android.kotlin_core.model.GetAuthGuestSessionNewResponseData
import com.android.kotlin_core.network.ApiConstant
import com.android.kotlin_core.network.TaskService
import com.android.kotlin_core.util.AppConst

open class GetAuthGuestSessionNewTask(override var postData: GetAuthGuestSessionNewRequestData, var listener: TaskListener) : TaskService<GetAuthGuestSessionNewRequestData, GetAuthGuestSessionNewResponseData>() {

    interface TaskListener {
        fun onSucceed(result: GetAuthGuestSessionNewResponseData)
        fun onFailed(error: String)
    }

    override fun onTaskFailed(error: String) {
        listener.onFailed(error)
    }

    override fun onTaskSucceed(result: GetAuthGuestSessionNewResponseData) {
        val response : GetAuthGuestSessionNewResponseData = result
        listener.onSucceed(response)
    }

    override fun getEndPoint(): String {
        return ApiConstant.GET_AUTH_GUEST_SESSION_NEW
    }

    override fun getMethod(): AppConst.HttpMethodType {
        return AppConst.HttpMethodType.GET
    }

}