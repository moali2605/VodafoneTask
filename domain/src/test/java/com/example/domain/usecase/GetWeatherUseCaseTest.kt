package com.example.domain.usecase

import com.example.core.util.DataHolder
import com.example.domain.model.WeatherDomainModel
import com.example.domain.repository.CityRepositoryInterface
import com.example.domain.repository.WeatherRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GetWeatherUseCaseTest {

    private lateinit var repositoryInterface: WeatherRepositoryInterface
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setUp() {
        repositoryInterface = mockk()
        getWeatherUseCase = GetWeatherUseCase(repositoryInterface)
    }

    @Test
    fun `execute should return weather`()=runBlocking {
        //GIVEN
        val expectedWeather = WeatherDomainModel(
            "",
            0.0,
            0.0,
            0,
            0,
            0.0,
            0.0,
            "",
            "",
            listOf(
                WeatherDomainModel.DailyWeather(0.0, 0.0, "", 0.0, "")
            )
        )
        coEvery { getWeatherUseCase.execute(0.0,0.0) } returns DataHolder.Success(expectedWeather)

        //WHEN
        val result = getWeatherUseCase.execute(0.0,0.0)

        //THEN
        assertEquals(expectedWeather,result.data)
    }
}