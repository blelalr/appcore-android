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
    override fun getAllSiteList(): LiveData<List<SiteModel>> {
        val data = MutableLiveData<List<SiteModel>>()
        var allSiteList : List<SiteModel> = mutableListOf()
        thread {
            allSiteList = if (aqiDao.getAllSite().isNullOrEmpty()) {
                getDefaultList()
            } else {
                aqiDao.getAllSite()
            }
            data.postValue(allSiteList)
        }
        return data
    }

    override fun getAllFollowedSite(): LiveData<List<SiteModel>> {
        val data = MutableLiveData<List<SiteModel>>()
        var allFollowSiteList : List<SiteModel> = mutableListOf()
        thread {
            allFollowSiteList = if (aqiDao.getAllSite().isNullOrEmpty()) {
                getDefaultList()
            } else {
                aqiDao.getAllFollowSite()
            }
            data.postValue(allFollowSiteList)
        }
        return data
    }

    private fun getDefaultList() : List<SiteModel> {
        val allSiteList : MutableList<SiteModel> = mutableListOf()
        aqiDataTask = AqiDataTask(object: AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {
                result?.forEach {
                    var tempSite = SiteModel(siteId = it.siteId.toString())
                    tempSite.siteName = it.siteName
                    tempSite.county = it.county
                    tempSite.latitude = it.latitude
                    tempSite.longitude = it.longitude
                    tempSite.aQI = it.aQI
                    tempSite.publishTime = it.publishTime
                    tempSite.isFollow = it.siteId.equals("12")
                    allSiteList.add(tempSite)
                    aqiDao.insertSite(tempSite)
                }
            }

            override fun onFailed(error: ResponseBody?) {
                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()

        return allSiteList.toList()
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
            override fun onSucceed(result: List<AqiModel>?) {
                result!!.forEach {aqi ->
                    if(aqiDao.getAll().isNullOrEmpty()) {
                        if(aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) return@forEach
                        val siteId = aqi.siteId.toLong()
                        val publishTime = DateUtil.DateToStamp(aqi.publishTime)
                        aqi.id = siteId + publishTime
                        aqiDao.insertAqi(aqi)
                    } else {
                        if(aqiDao.filterBySite(aqi.siteId, aqi.publishTime).isNullOrEmpty()) return@forEach  //表示有重複的資料
                        Log.d("esther", "insert! : " +aqi.siteName +"  "+ aqi.publishTime.toString() + " " +"AQI ${aqi.aQI}")
                        if(aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) return@forEach
                        val siteId = aqi.siteId.toLong()
                        val publishTime = DateUtil.DateToStamp(aqi.publishTime)
                        aqi.id = siteId + publishTime
                        aqiDao.insertAqi(aqi)
                    }
                }
            }

            override fun onFailed(error: ResponseBody?) {
                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()
    }

    override fun isFollowed(siteId: String): Boolean {
        var isFollow = false
        thread {
            isFollow = aqiDao.getIsFollow(siteId)
        }
        return isFollow
    }

    override fun updateFollowSiteList() {
        getAllFollowedSite()
    }
}