package com.example.domain.usecase

import com.example.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repo: RepositoryInterface){
    suspend fun execute(lat: Double, lon: Double) = repo.getWeather(lat, lon)
}