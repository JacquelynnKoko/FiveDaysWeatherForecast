package com.example.weatherForecast.repo

import com.example.weatherForecast.viewModel.WeatherViewModel
import com.example.weatherForecast.dao.WeatherDao
import com.example.weatherForecast.database.DataBase
import com.example.weatherForecast.entity.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object WeatherRepo {

    private val dao: WeatherDao by lazy {
        DataBase.getWeather()
    }

    init {
        WeatherViewModel().weatherListLiveData.observeForever {
            GlobalScope.launch(Dispatchers.IO) {
                for(i in 0 until it.size) {
                    val list = WeatherEntity(it[i].date, it[i].main.temp, it[i].weather.description)
                    dao.add(list)
                }

            }
        }
    }

    fun getWeatherFromDb() : List<WeatherEntity>{
       return dao.getAll()
    }
}