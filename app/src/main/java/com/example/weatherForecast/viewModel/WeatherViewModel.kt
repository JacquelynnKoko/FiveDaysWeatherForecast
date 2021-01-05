package com.example.weatherForecast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherForecast.model.WeatherResponse
import com.example.weatherForecast.restCalls.RestCallApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    val weatherListLiveData = MutableLiveData<ArrayList<WeatherResponse>>()

    fun updateWeatherData(city: String) {
        var response = ArrayList<WeatherResponse>()
        CoroutineScope(Dispatchers.IO).launch {
            response = RestCallApi.getJSON(city)
        }.invokeOnCompletion {
            weatherListLiveData.postValue(response)
        }
    }
}