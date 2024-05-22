package com.example.domain.usecase

import com.example.domain.model.CityDomainModel
import com.example.domain.repository.CityRepositoryInterface
import javax.inject.Inject

class InsertCityUseCase @Inject constructor(private val repo: CityRepositoryInterface) {
    suspend fun execute(city: CityDomainModel) = repo.insertCity(city)
}