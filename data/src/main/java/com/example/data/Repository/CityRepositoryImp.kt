package com.example.data.Repository

import android.util.Log
import com.example.core.util.DataHolder
import com.example.data.local.LocalSourceInterface
import com.example.data.mapper.toCityDomainModel
import com.example.data.mapper.toCityEntity
import com.example.domain.model.CityDomainModel
import com.example.domain.repository.CityRepositoryInterface
import javax.inject.Inject

class CityRepositoryImp @Inject constructor(
    private val localSourceInterface: LocalSourceInterface
) : CityRepositoryInterface {
    override suspend fun getAllCities(): DataHolder<List<CityDomainModel>> {
        return try {
            val cityEntities = localSourceInterface.getAllCities()
            val cityDomainModels = cityEntities.map { it.toCityDomainModel() }
            DataHolder.Success(cityDomainModels)
        } catch (e: Exception) {
            Log.e("okhttp", "getAllCities: ${e.message}")
            DataHolder.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun insertCity(city: CityDomainModel) {
        localSourceInterface.insertCities(city.toCityEntity())
    }
}