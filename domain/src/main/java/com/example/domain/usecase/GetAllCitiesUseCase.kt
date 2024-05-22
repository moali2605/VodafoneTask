package com.example.domain.usecase

import com.example.domain.repository.CityRepositoryInterface
import javax.inject.Inject

class GetAllCitiesUseCase @Inject constructor(private val repo: CityRepositoryInterface) {
    suspend fun execute() = repo.getAllCities()
}