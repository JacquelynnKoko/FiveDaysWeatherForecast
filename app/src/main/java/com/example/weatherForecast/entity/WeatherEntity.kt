package com.example.weatherForecast.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "WeatherTable",
)
data class WeatherEntity (
    @PrimaryKey
    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "temp")
    var temp: String,

    @ColumnInfo(name = "weatherCondition")
    var weatherCondition: String
)