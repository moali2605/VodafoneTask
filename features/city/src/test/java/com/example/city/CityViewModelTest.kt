package com.example.city

import com.example.city.view_model.CityViewModel
import com.example.core.util.DataHolder
import com.example.domain.model.CityDomainModel
import com.example.domain.model.CitySearchResponseDomainModel
import com.example.domain.usecase.GetAllCitiesUseCase
import com.example.domain.usecase.InsertCityUseCase
import com.example.domain.usecase.SearchForCityByNameUseCae
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityViewModelTest {
    private lateinit var getAllCitiesUseCase: GetAllCitiesUseCase
    private lateinit var searchForCityByNameUseCae: SearchForCityByNameUseCae
    private lateinit var insertCityUseCase: InsertCityUseCase
    private lateinit var viewModel: CityViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        getAllCitiesUseCase = mockk()
        searchForCityByNameUseCae = mockk()
        insertCityUseCase = mockk()
        viewModel = CityViewModel(searchForCityByNameUseCae, getAllCitiesUseCase, insertCityUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchForCityByName should return city info`() = runTest {
        // Given
        val expected = listOf(CitySearchResponseDomainModel("cairo", 0.0, 0.0, "", ""))
        coEvery { searchForCityByNameUseCae.execute("cairo") } returns DataHolder.Success(expected)

        // When
        viewModel.searchForCityByName("cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(expected, viewModel.searchForCityByNameState.value.cityInfoResponse)
        coVerify { searchForCityByNameUseCae.execute("cairo") }
    }

    @Test
    fun `searchForCityByName should return network error`() = runTest {
        testScope.launch {
            // Given
            val errorMessage = "Network error"
            coEvery { searchForCityByNameUseCae.execute("cairo") } returns DataHolder.Failure(
                errorMessage
            )

            // When
            viewModel.searchForCityByName("cairo")
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val errorState =
                viewModel.searchForCityByNameErrorState.first() // Collect the first emitted value
            assertEquals(errorMessage, errorState.errorMsg)
            coVerify { searchForCityByNameUseCae.execute("cairo") }
        }
    }

    @Test
    fun `getCities should return cities`() = runTest {
        // GIVEN
        val expected = listOf(CityDomainModel("cairo", 0.0, 0.0))
        coEvery { getAllCitiesUseCase.execute() } returns DataHolder.Success(expected)

        // WHEN
        viewModel.getCities()
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        assertEquals(expected, viewModel.stateGetCities.value.cityResponse)
        coVerify { getAllCitiesUseCase.execute() }
    }

    @Test
    fun `getCities should return error`() = runTest {
        testScope.launch {
            // GIVEN
            val errorMassage = "error"
            coEvery { getAllCitiesUseCase.execute() } returns DataHolder.Failure(errorMassage)

            // WHEN
            viewModel.getCities()
            testDispatcher.scheduler.advanceUntilIdle()

            // THEN
            val errorState = viewModel.getCitiesErrorState.first().errorMsg
            assertEquals(errorMassage, errorState)
            coVerify { getAllCitiesUseCase.execute() }
        }
    }

    @Test
    fun `insertCity should return success`() = runTest {
        // GIVEN
        val city = CityDomainModel("cairo", 0.0, 0.0)
        coEvery { insertCityUseCase.execute(city) } returns Unit

        // WHEN
        viewModel.insertCity(city)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { insertCityUseCase.execute(city) }
    }
}
