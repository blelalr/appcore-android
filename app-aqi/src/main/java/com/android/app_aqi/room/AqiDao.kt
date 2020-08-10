package com.android.app_aqi.room

import androidx.room.*
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

@Dao
interface AqiDao {

    //TABLE AQI
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAqi(aqi: AqiModel)

    @Query("SELECT * FROM AQI")
    fun getAll(): List<AqiModel>

    @Query("SELECT * FROM AQI WHERE siteId= :siteId AND publishTime= :publishTime")
    fun filterBySite(siteId: String?, publishTime: String?): List<AqiModel>

    //TABLE ALL_SITE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSite(it: SiteModel)

    @Query("SELECT * FROM ALL_SITE")
    fun getAllSite(): List<SiteModel>

    @Query("SELECT * FROM ALL_SITE WHERE isFollow")
    fun getAllFollowSite(): List<SiteModel>

    @Query("UPDATE ALL_SITE SET isFollow = 1  WHERE siteId=:siteId")
    fun followSite(siteId: String)

    @Query("UPDATE ALL_SITE SET isFollow = 0  WHERE siteId=:siteId")
    fun unFollowSite(siteId: String)

    @Query("SELECT isFollow FROM ALL_SITE WHERE siteId = :siteId")
    fun getIsFollow(siteId: String): Boolean

}