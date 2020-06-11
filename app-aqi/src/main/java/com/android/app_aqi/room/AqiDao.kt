package com.android.app_aqi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.app_aqi.model.AqiModel

@Dao
interface AqiDao {

    @Query("SELECT * FROM AQI")
    fun getAll(): List<AqiModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aqi: AqiModel): Long

}