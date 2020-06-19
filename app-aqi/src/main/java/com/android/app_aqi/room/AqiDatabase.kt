package com.android.app_aqi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.app_aqi.model.AqiModel

@Database(entities = [(AqiModel::class)], version = 1, exportSchema = false)
abstract class AqiDatabase : RoomDatabase(){

    companion object {

        const val DATABASE_NAME = "aqi_database"

    }

    abstract fun getAqiDao():AqiDao
}