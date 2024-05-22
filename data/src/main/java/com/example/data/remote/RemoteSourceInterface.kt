package com.example.data.remote

import com.example.data.model.CitySearchResponse
import com.example.data.model.WeatherResponse
import retrofit2.Response


interface RemoteSourceInterface {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): Response<WeatherResponse>

    suspend fun searchForCityByName(cityName:String):Response<List<CitySearchResponse>>
}