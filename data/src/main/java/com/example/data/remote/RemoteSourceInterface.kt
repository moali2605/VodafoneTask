package com.example.data.remote

import com.example.data.model.CityResponse
import com.example.data.model.WeatherResponse
import retrofit2.Response


interface RemoteSourceInterface {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): Response<WeatherResponse>

    suspend fun getCity(cityName:String):Response<List<CityResponse>>
}