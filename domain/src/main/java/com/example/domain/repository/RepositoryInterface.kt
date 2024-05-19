package com.example.domain.repository

import com.example.core.util.DataHolder
import com.example.domain.model.WeatherDomainModel
import com.example.myapplication.domain.model.CityDomainModel

interface RepositoryInterface {
    suspend fun getWeather(lat: Double, lon: Double): DataHolder<WeatherDomainModel>
    suspend fun getAllCities(): DataHolder<List<CityDomainModel>>
    suspend fun insertCity(city: CityDomainModel)
}