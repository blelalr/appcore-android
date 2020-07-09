package com.android.app_aqi.room

import androidx.room.*
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

@Dao
interface AqiDao {

    @Query("SELECT * FROM AQI")
    fun getAll(): List<AqiModel>

    @Query("SELECT * FROM AQI WHERE siteId= :siteId AND publishTime= :publishTime")
    fun filterBySite(siteId: String?, publishTime: String?): List<AqiModel>

    @Query("SELECT siteId, siteName, county ,longitude, latitude, aqi, MAX(publishTime) FROM AQI GROUP by siteId")
    fun getSiteList(): List<SiteModel>

    @Query("SELECT siteId, siteName, county ,longitude, latitude, aqi, max(publishTime)  FROM AQI WHERE siteId IN (:followedSites) GROUP by siteId")
    fun getAllFollowSite(followedSites: List<String>): List<SiteModel>

    @Query("SELECT siteId, siteName, county ,longitude, latitude, aqi, max(publishTime)  FROM AQI WHERE siteId= :followedSiteId GROUP by siteId")
    fun getFollowSiteById(followedSiteId: String): SiteModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aqi: AqiModel): Long

}