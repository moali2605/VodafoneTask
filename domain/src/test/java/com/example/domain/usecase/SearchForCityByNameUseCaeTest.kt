package com.example.domain.usecase

import com.example.core.util.DataHolder
import com.example.domain.model.CitySearchResponseDomainModel
import com.example.domain.repository.WeatherRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class SearchForCityByNameUseCaeTest {

    private lateinit var repositoryInterface: WeatherRepositoryInterface
    private lateinit var searchForCityByNameUseCase: SearchForCityByNameUseCae

    @Before
    fun setUp() {
        repositoryInterface = mockk()
        searchForCityByNameUseCase = SearchForCityByNameUseCae(repositoryInterface)
    }
    @Test
    fun `execute return a list of cities`()=runBlocking {
        //GIVEN
        val cityName = "cairo"
        val cityResponse = listOf(
            CitySearchResponseDomainModel(
                name = cityName,
                latitude = 31.0409,
                longitude = 31.3785,
                state = "",
                country = ""
            )
        )
        coEvery { searchForCityByNameUseCase.execute(cityName) } returns DataHolder.Success(cityResponse)

        //WHEN
        val result = searchForCityByNameUseCase.execute(cityName)

        //THEN
        assertNotNull(result)
        assertEquals(cityResponse,result.data)
    }
}