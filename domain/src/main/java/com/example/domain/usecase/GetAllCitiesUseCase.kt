package com.example.domain.usecase

import com.example.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetAllCitiesUseCase @Inject constructor(private val repo: RepositoryInterface) {
    suspend fun execute() = repo.getAllCities()
}