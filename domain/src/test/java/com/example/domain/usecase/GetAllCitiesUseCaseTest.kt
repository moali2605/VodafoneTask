package com.example.domain.usecase

import com.example.core.util.DataHolder
import com.example.domain.model.CityDomainModel
import com.example.domain.repository.CityRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GetAllCitiesUseCaseTest {

    private lateinit var repositoryInterface: CityRepositoryInterface
    private lateinit var allCitiesUseCase: GetAllCitiesUseCase

    @Before
    fun setUp() {
        repositoryInterface = mockk()
        allCitiesUseCase = GetAllCitiesUseCase(repositoryInterface)
    }

    @Test
    fun `execute should return all cities`() = runBlocking{
        //GIVEN
        val expectedCities = listOf(CityDomainModel("cairo",0.0,0.0))
        coEvery { repositoryInterface.getAllCities() } returns DataHolder.Success(expectedCities)

        //WHEN
        val result = allCitiesUseCase.execute()

        //THEN
        assertEquals(expectedCities, result.data)
    }
}