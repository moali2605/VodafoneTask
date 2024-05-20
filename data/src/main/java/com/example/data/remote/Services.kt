package com.example.data.remote

import com.example.core.util.Constants
import com.example.data.model.CityResponse
import com.example.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String = Constants.API_KEY
    ): Response<WeatherResponse>

    @GET("/geo/1.0/direct")
    suspend fun getCity(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<List<CityResponse>>
}