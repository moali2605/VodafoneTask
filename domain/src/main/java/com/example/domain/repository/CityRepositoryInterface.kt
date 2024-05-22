package com.example.domain.repository

import com.example.core.util.DataHolder
import com.example.domain.model.CityDomainModel

interface CityRepositoryInterface {
    suspend fun getAllCities(): DataHolder<List<CityDomainModel>>
    suspend fun insertCity(city: CityDomainModel)
}