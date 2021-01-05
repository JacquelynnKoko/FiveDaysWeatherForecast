package com.example.weatherForecast.restCalls

import com.example.weatherForecast.model.WeatherResponse
import com.github.ajalt.timberkt.Timber
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


object RestCallApi {
    private const val API_KEY = "0fdfc50e9ac48d33ff7f0907d897a197"

    private const val API_5DAYS_WEATHER =
        "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid=$API_KEY&units=metric"


    fun getJSON(city: String?): ArrayList<WeatherResponse> {
        var response = ""
        try {
            response = URL(city?.let { API_5DAYS_WEATHER.replace("{city}", it) }).readText(Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.e(e)
        }
       return parseWeatherObject(JSONObject(response))
    }

    private fun parseWeatherObject(jsonObject: JSONObject) : ArrayList<WeatherResponse>{
        val parsedArrayList = ArrayList<WeatherResponse>()
        val list: JSONArray = jsonObject.getJSONArray("list")
        for (i in 0 until list.length()) {
            val response = WeatherResponse()
            val listJsonObject = list.getJSONObject(i)

            response.date = listJsonObject.getString("dt_txt")
            response.main.temp = listJsonObject.getJSONObject("main").getString("temp")

            val weatherArray = listJsonObject.getJSONArray("weather")
            val ob = weatherArray[0] as JSONObject
            response.weather.description = ob.getString("description")
            parsedArrayList.add(response)
        }

        return parsedArrayList
    }
}