package com.example.data.Repository

import com.example.data.local.CityDao
import com.example.data.local.LocalSourceImp
import com.example.data.mapper.toCityEntity
import com.example.domain.model.CityDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class CityRepositoryImpTest {
    private lateinit var cityRepositoryImp: CityRepositoryImp
    private lateinit var localSourceImp: LocalSourceImp

    @Before
    fun setUp() {
        localSourceImp = mockk()
        cityRepositoryImp = CityRepositoryImp(localSourceImp)
    }

    @Test
    fun `getAllCities should return all cities from local source`() = runBlocking {
        // Given
        val expected = listOf(
            CityDomainModel("cairo", 5.5, 5.5),
            CityDomainModel("mansoura", 35.5, 37.0),
            CityDomainModel("new york", 0.0, 2.2)
        )
        coEvery { localSourceImp.getAllCities() } returns expected.map { it.toCityEntity() }

        // When
        val result = cityRepositoryImp.getAllCities().data

        // Then
        assertEquals(expected, result)

    }

    @Test
    fun `insertCity should insert city to local source`() = runBlocking {
        // Given
        val city = CityDomainModel("cairo", 5.5, 5.5)
        coEvery { localSourceImp.insertCities(city.toCityEntity()) } returns Unit
        // When
        cityRepositoryImp.insertCity(city)
        // Then
        coVerify { localSourceImp.insertCities(city.toCityEntity()) }
    }
}