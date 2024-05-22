package com.example.domain.usecase

import com.example.domain.repository.WeatherRepositoryInterface
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repo: WeatherRepositoryInterface) {
    suspend fun execute(lat: Double, lon: Double) = repo.getWeather(lat, lon)
}