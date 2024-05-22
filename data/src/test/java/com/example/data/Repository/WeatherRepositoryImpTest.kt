package com.example.data.Repository

import com.example.data.mapper.toCityResponseDomainModel
import com.example.data.mapper.toWeatherDomainModel
import com.example.data.model.CitySearchResponse
import com.example.data.model.WeatherResponse
import com.example.data.remote.RemoteSourceImp
import com.example.domain.model.CityDomainModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Response

class WeatherRepositoryImpTest {
    private lateinit var weatherRepositoryImp: WeatherRepositoryImp
    private lateinit var remoteSourceImp: RemoteSourceImp

    @Before
    fun setUp() {
        remoteSourceImp = mockk()
        weatherRepositoryImp = WeatherRepositoryImp(remoteSourceImp)
    }

    @Test
    fun `getWeather should return weather data`() = runBlocking {
        // GIVEN
        val city = CityDomainModel("cairo", 31.0409, 31.3785)
        val weatherResponse =
            WeatherResponse(
                0.0, 0.0, "", 1,
                WeatherResponse.CurrentWeather(
                    1,
                    1,
                    0,
                    0.0,
                    0.0,
                    0,
                    0,
                    0.0,
                    0.0,
                    0,
                    0,
                    0.0,
                    0,
                    0.0,
                    listOf(WeatherResponse.Weather(0, "", "", ""))
                ),
                emptyList(), emptyList(), emptyList(), emptyList()
            )
        coEvery {
            remoteSourceImp.getWeather(
                city.cityLat,
                city.cityLong
            )
        } returns Response.success(
            weatherResponse
        )

        // WHEN
        val result = weatherRepositoryImp.getWeather(city.cityLat, city.cityLong)

        // THEN
        assertNotNull(result)
        assertEquals(weatherResponse.toWeatherDomainModel(), result.data)
    }

    @Test
    fun `searchForCityByName should return city data`() = runBlocking {
        //GIVEN
        val cityName = "cairo"
        val citySearchResponses = listOf(
            CitySearchResponse(
                name = cityName,
                latitude = 31.0409,
                longitude = 31.3785,
                state = "",
                country = ""
            ),
            CitySearchResponse(
                name = cityName,
                latitude = 31.0409,
                longitude = 31.3785,
                state = "",
                country = ""
            )
        )
        coEvery { remoteSourceImp.searchForCityByName(cityName) } returns Response.success(
            citySearchResponses
        )

        //WHEN
        val result = weatherRepositoryImp.searchForCityByName(cityName)

        //THEN
        assertNotNull(result)
        assertEquals(citySearchResponses.map { it.toCityResponseDomainModel() },result.data)
    }
}