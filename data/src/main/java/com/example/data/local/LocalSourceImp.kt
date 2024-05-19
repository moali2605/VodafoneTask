package com.example.data.local

import com.example.data.model.CityEntity
import javax.inject.Inject

class LocalSourceImp @Inject constructor(private val cityDao: CityDao) : LocalSourceInterface {
    override suspend fun getAllCities(): List<CityEntity> {
        return cityDao.getAllCities()
    }

    override suspend fun insertCities(city: CityEntity) {
        cityDao.addCity(city)
    }
}