package com.example.data.Repository

import com.example.core.util.DataHolder
import com.example.data.local.LocalSourceInterface
import com.example.data.mapper.toCityDomainModel
import com.example.data.mapper.toCityEntity
import com.example.data.mapper.toWeatherDomainModel
import com.example.data.remote.RemoteSourceInterface
import com.example.domain.repository.RepositoryInterface
import com.example.domain.model.WeatherDomainModel
import com.example.myapplication.domain.model.CityDomainModel
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val remoteSourceInterface: RemoteSourceInterface,
    private val localSourceInterface: LocalSourceInterface
) :
    RepositoryInterface {
    override suspend fun getWeather(lat: Double, lon: Double): DataHolder<WeatherDomainModel> {
        remoteSourceInterface.getWeather(
            lat = lat,
            lon = lon,
            "metric",
            lang = "en",
            appId = "306181262de40d435bac69900d1ef801"
        ).also { result ->
            return if (result.isSuccessful) {
                result.body()?.let {
                    DataHolder.Success(it.toWeatherDomainModel())
                } ?: DataHolder.Failure("No body in response")
            } else {
                DataHolder.Failure(result.message())
            }
        }
    }

    override suspend fun getAllCities(): DataHolder<List<CityDomainModel>> {
        return try {
            val cityEntities = localSourceInterface.getAllCities()
            val cityDomainModels = cityEntities.map { it.toCityDomainModel() }
            DataHolder.Success(cityDomainModels)
        } catch (e: Exception) {
            DataHolder.Failure(e.message ?: "Unknown error")
        }
    }


    override suspend fun insertCity(city: CityDomainModel) {
        localSourceInterface.insertCities(city.toCityEntity())
    }

}