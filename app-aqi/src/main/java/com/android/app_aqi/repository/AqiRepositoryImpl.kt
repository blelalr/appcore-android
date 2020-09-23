package com.android.app_aqi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.app_aqi.Constant
import com.android.app_aqi.DateUtil
import com.android.app_aqi.api.TaskManager
import com.android.app_aqi.aqiDb
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.RequestEntity
import com.android.app_aqi.model.SiteModel
import com.android.app_aqi.room.AqiDao
import com.android.app_aqi.task.AqiDataTask
import okhttp3.ResponseBody
import kotlin.concurrent.thread

class AqiRepositoryImpl : AqiRepository {
    private val aqiDao: AqiDao = aqiDb.aqiDao()
    private lateinit var aqiDataTask: AqiDataTask
    private lateinit var followSiteList: MutableLiveData<List<SiteModel>>
    private lateinit var allSiteList: MutableLiveData<List<SiteModel>>
    private lateinit var defaultSiteList: MutableList<SiteModel>
    private lateinit var last12HourAqiDataList: MutableLiveData<List<AqiModel>>
    private lateinit var lastAqiDataList: MutableLiveData<List<AqiModel>>
    override fun getAllSiteList(): LiveData<List<SiteModel>> {
        allSiteList = MutableLiveData()
        thread {
            if (!aqiDao.getAllSite().isNullOrEmpty()) {
                allSiteList.postValue(aqiDao.getAllSite())
            }
        }
        return allSiteList
    }

    override fun getAllFollowedSite(): LiveData<List<SiteModel>> {
        followSiteList = MutableLiveData()
        thread {
            if (aqiDao.getAllSite().isNullOrEmpty()) {
                Log.d("esther", "getDefaultList")
                getDefaultList()
            } else {
                Log.d("esther", "All from DB")
                followSiteList.postValue(aqiDao.getAllFollowSite())
            }
        }
        return followSiteList
    }

    private fun getDefaultList() {
        defaultSiteList = mutableListOf()
        aqiDataTask = AqiDataTask(object: AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>) {
                result.forEach {
                    val tempSite = SiteModel(siteId = it.siteId.toString())
                    tempSite.siteName = it.siteName
                    tempSite.county = it.county
                    tempSite.latitude = it.latitude
                    tempSite.longitude = it.longitude
                    tempSite.aQI = it.aQI
                    tempSite.publishTime = it.publishTime
                    tempSite.isFollow = it.siteId.equals("12")
                    aqiDao.insertSite(tempSite)
                    if(tempSite.isFollow!!)
                        defaultSiteList.add(tempSite)
                    Log.d("esther", "add $tempSite")
                }
                followSiteList.postValue(defaultSiteList)
                Log.d("esther", "postValue(defaultSiteList) : $followSiteList")
            }

            override fun onFailed(error: ResponseBody) {
                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()

    }

    override fun followSite(siteId: String) {
        thread {
            aqiDao.followSite(siteId)
        }
    }

    override fun unFollowSite(siteId: String) {
        thread {
            aqiDao.unFollowSite(siteId)
        }
    }

    override fun insertAqiData() {
        aqiDataTask = AqiDataTask(object : AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>) {
                result.forEach { aqi ->
                    //有資料時,表示有重複的資料
                    if(aqiDao.getAll().isNotEmpty() && aqiDao.filterBySite(aqi.siteId, aqi.publishTime).isNotEmpty()) {
//                        Log.d("esther", "有重複的資料")
                        return
                    }
                    if (aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) {
//                        Log.d("esther", "siteId null or publishTime null")
                        return@forEach
                    } else {
                        val siteId = aqi.siteId.toLong()
                        val publishTime = DateUtil.covertDateToTimestamp(aqi.publishTime)
                        aqi.id = siteId + publishTime
                        aqiDao.insertAqi(aqi)
//                        Log.d("esther", "insert! : " + aqi.siteName + "  " + aqi.publishTime.toString() + " " + "AQI ${aqi.aQI}")
                    }
                }
            }

            override fun onFailed(error: ResponseBody) {
//                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()
    }

    override fun isFollowed(siteId: String): Boolean {
        var isFollow = false
        thread {
            isFollow = aqiDao.isFollow(siteId)
        }
        return isFollow
    }

    override fun updateFollowSiteList() {
        getAllFollowedSite()
    }

    override fun getLast12HourAqiDataBySiteId(siteId: String): LiveData<List<AqiModel>> {
        last12HourAqiDataList = MutableLiveData()
        thread {
            if (!aqiDao.getAll().isNullOrEmpty()) {
                last12HourAqiDataList.postValue(aqiDao.getLast12HourAqiDataBySiteId(siteId))
            }
        }
        return last12HourAqiDataList
    }

    override fun getLastAqiDataBySiteIdList(followSiteIdList: List<SiteModel>):  LiveData<List<AqiModel>>{
       lastAqiDataList = MutableLiveData()
        val tempList : MutableList<AqiModel>  = mutableListOf()
        thread {
            if (!aqiDao.getAll().isNullOrEmpty()) {
                followSiteIdList.forEach {
                    tempList.add(aqiDao.getLastAqiDataBySiteId(it.siteId))
                }
                lastAqiDataList.postValue(tempList)

            }
        }
        return lastAqiDataList
    }
}