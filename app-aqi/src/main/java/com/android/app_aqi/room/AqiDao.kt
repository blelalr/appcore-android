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
//
    @Query("SELECT siteId, siteName, county ,longitude, latitude, aqi, MAX(publishTime) FROM AQI GROUP by siteId")
    fun getAllSiteList(): List<SiteModel>
//
//

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAqi(aqi: AqiModel): Long


    //Table FollowSite
    @Query("SELECT * FROM FollowedSite")
    fun getAllFollowSite(): List<SiteModel>

    @Query("SELECT * FROM FollowedSite WHERE siteId= :followedSiteId")
    fun getFollowSiteById(followedSiteId: String): SiteModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun followSite(site: SiteModel): Long

    @Query("DELETE FROM FollowedSite WHERE siteId = :id")
    fun unFollowSite(id: Int?)



}