package com.example.weatherForecast.dao

import androidx.room.*
import com.example.weatherForecast.entity.WeatherEntity
@Dao
interface WeatherDao {
    @Query("SELECT * FROM WEATHERTABLE")
    fun getAll(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(weatherForecast : WeatherEntity)

    @Delete
    fun delete(todo: WeatherEntity)

    @Update
    fun updateTodo(vararg todos: WeatherEntity)

}