package com.example.domain.usecase

import com.example.domain.repository.RepositoryInterface
import com.example.myapplication.domain.model.CityDomainModel
import javax.inject.Inject

class InsertCityUseCase @Inject constructor(private val repo: RepositoryInterface) {
    suspend fun execute(city: CityDomainModel) = repo.insertCity(city)
}