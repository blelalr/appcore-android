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

class AqiRepositoryImpl : AqiRepository {
    private val aqiDao: AqiDao = aqiDb.aqiDao()
    private lateinit var aqiDataTask: AqiDataTask
    private val defaultSiteId = "12"
    private val all = "-1"
    override fun getAllSiteList(): List<SiteModel> {
        return if(aqiDao.getAll().isNullOrEmpty() && aqiDao.getAllSiteList().isNullOrEmpty()){
            getDefaultList(all)
        } else {
            aqiDao.getAllSiteList()
        }
    }

    override fun getAllFollowedSite(): LiveData<List<SiteModel>> {
        val data = MutableLiveData<List<SiteModel>>()
        if(aqiDao.getAll().isNullOrEmpty() && aqiDao.getAllFollowSite().isNullOrEmpty()){
            data.value = getDefaultList(defaultSiteId)
        } else {
            data.value = aqiDao.getAllFollowSite()
        }
        return data
    }

    private fun getDefaultList(siteId : String) : List<SiteModel> {
        val allSiteList : MutableList<SiteModel> = mutableListOf()
        val defaultList : MutableList<SiteModel> = mutableListOf()

        aqiDataTask = AqiDataTask(object: AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {
                result?.forEach {
                    if (siteId == all) {
                        var tempSite = SiteModel()
                        tempSite.siteId = it.siteId
                        tempSite.siteName = it.siteName
                        tempSite.county = it.county
                        tempSite.latitude = it.latitude
                        tempSite.longitude = it.longitude
                        tempSite.aQI = it.aQI
                        allSiteList.add(tempSite)
                    } else if (it.siteId.equals(siteId)) {
                        var defaultSite = SiteModel()
                        defaultSite.siteId = it.siteId
                        defaultSite.siteName = it.siteName
                        defaultSite.county = it.county
                        defaultSite.latitude = it.latitude
                        defaultSite.longitude = it.longitude
                        defaultSite.aQI = it.aQI
                        defaultList.add(defaultSite)
                    }
                }
            }

            override fun onFailed(error: ResponseBody?) {
                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()

        return if (siteId == all) {
            allSiteList.toList()
        } else {
            defaultList.toList()
        }
    }

    override fun followSite(site: SiteModel) {
        aqiDao.followSite(site)
    }

    override fun unFollowSite(siteId: Int) {
        aqiDao.unFollowSite(siteId)
    }

    override fun insertAqiData() {
        aqiDataTask = AqiDataTask(object : AqiDataTask.TaskListener {
            override fun onSucceed(result: List<AqiModel>?) {
                result!!.forEach {aqi ->
                    if(aqiDao.filterBySite(aqi.siteId, aqi.publishTime).isEmpty()) return@forEach  //表示有重複的資料
                    Log.d("esther", "insert! : " +aqi.siteName +"  "+ aqi.publishTime.toString() + " " +"AQI ${aqi.aQI}")
                    if(aqi.siteId.isNullOrEmpty() || aqi.publishTime.isNullOrEmpty()) return@forEach
                    val siteId = aqi.siteId.toLong()
                    val publishTime = DateUtil.DateToStamp(aqi.publishTime)
                    aqi.id = siteId + publishTime
                    aqiDao.insertAqi(aqi)
                }
            }

            override fun onFailed(error: ResponseBody?) {
                Log.d("esther", "Fail $error")
            }

        }, RequestEntity(Constant.top, 0, "json", ""))
        TaskManager(aqiDataTask).start()
    }

    override fun isFollow(siteId: String): Boolean {
        if(aqiDao.getFollowSiteById(siteId)) {

        } else {
            return true
        }
    }
}