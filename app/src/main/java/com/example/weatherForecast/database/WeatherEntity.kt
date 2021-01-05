package com.example.weatherForecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherForecast.dao.WeatherDao
import com.example.weatherForecast.entity.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeather(): WeatherDao
}

object DataBase {

    private lateinit var db: WeatherDatabase
    fun init(context: Context) {
        db = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java).build()
    }

    fun getWeather() = db.getWeather()
}