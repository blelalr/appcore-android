package com.android.app_aqi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

@Dao
interface AqiDao {

    @Query("SELECT * FROM AQI")
    fun getAll(): List<AqiModel>

    @Query("SELECT * FROM AQI WHERE siteId= :siteId AND publishTime= :publishTime")
    fun filterBySite(siteId: String?, publishTime: String?): List<AqiModel>

    @Query("SELECT DISTINCT siteId, siteName, county, aQI ,longitude, latitude FROM AQI")
    fun getSiteList(): List<SiteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aqi: AqiModel): Long

}