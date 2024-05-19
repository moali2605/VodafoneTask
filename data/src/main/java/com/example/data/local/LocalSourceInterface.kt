package com.example.data.local

import com.example.data.model.CityEntity

interface LocalSourceInterface {
    suspend fun getAllCities(): List<CityEntity>
    suspend fun insertCities(city: CityEntity)
}