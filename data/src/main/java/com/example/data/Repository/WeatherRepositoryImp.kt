package com.example.data.Repository

import com.example.core.util.DataHolder
import com.example.data.mapper.toCityResponseDomainModel
import com.example.data.mapper.toWeatherDomainModel
import com.example.data.remote.RemoteSourceInterface
import com.example.domain.model.CitySearchResponseDomainModel
import com.example.domain.model.WeatherDomainModel
import com.example.domain.repository.WeatherRepositoryInterface
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val remoteSourceInterface: RemoteSourceInterface
) :
    WeatherRepositoryInterface {
    override suspend fun getWeather(lat: Double, lon: Double): DataHolder<WeatherDomainModel> {
        remoteSourceInterface.getWeather(
            lat = lat,
            lon = lon
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

    override suspend fun searchForCityByName(cityName: String): DataHolder<List<CitySearchResponseDomainModel>> {
        remoteSourceInterface.searchForCityByName(cityName).also { result ->
            return if (result.isSuccessful) {
                result.body()?.let {
                    DataHolder.Success(it.map { cityResponse ->
                        cityResponse.toCityResponseDomainModel()
                    })
                } ?: DataHolder.Failure("No body in response")
            } else {
                DataHolder.Failure(result.message())
            }
        }
    }

}