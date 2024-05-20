package com.example.domain.usecase

import com.example.domain.repository.RepositoryInterface
import javax.inject.Inject

class GetCityInfoUseCase @Inject constructor(private val repo: RepositoryInterface) {
    suspend fun execute(citName: String) = repo.getCityInfo(citName)
}