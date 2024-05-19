package com.example.data.remote

import com.example.data.model.WeatherResponse
import retrofit2.Response


interface RemoteSourceInterface {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        appId: String
    ): Response<WeatherResponse>
}