package com.android.app_aqi.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.app_aqi.model.AqiModel
import com.android.app_aqi.model.SiteModel

@Database(entities = [(AqiModel::class), (SiteModel::class)], version = 1, exportSchema = false)
abstract class AqiDatabase : RoomDatabase(){

    abstract fun aqiDao():AqiDao

    companion object {
        private val lock = Any()
        private const val DATABASE_NAME = "aqi_database"
        private var INSTANCE: AqiDatabase? = null

        fun getInstance(application: Application): AqiDatabase {
            synchronized(AqiDatabase.lock) {
                if (AqiDatabase.INSTANCE == null) {
                    AqiDatabase.INSTANCE =
                            Room.databaseBuilder(application, AqiDatabase::class.java, AqiDatabase.DATABASE_NAME)
                                    .build()
                }
            }
            return INSTANCE!!
        }
    }

}