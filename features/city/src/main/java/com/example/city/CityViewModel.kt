package com.example.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.DataHolder
import com.example.domain.usecase.GetAllCitiesUseCase
import com.example.domain.usecase.InsertCityUseCase
import com.example.myapplication.domain.model.CityDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getAllCitiesUseCase: GetAllCitiesUseCase,
    private val insertCityUseCase: InsertCityUseCase
) : ViewModel() {
    private val _stateGetCities: MutableStateFlow<GetCityState.Display> =
        MutableStateFlow(GetCityState.Display())
    val stateGetCities = _stateGetCities.asStateFlow()
    private val _getCitiesErrorState: MutableSharedFlow<GetCityState.Failure> =
        MutableSharedFlow()
    val getCitiesErrorState = _getCitiesErrorState.asSharedFlow()

    fun getCities() {
        viewModelScope.launch {
            when (val result = getAllCitiesUseCase.execute()) {
                is DataHolder.Failure -> {
                    _getCitiesErrorState.emit(GetCityState.Failure(result.error!!))
                }
                is DataHolder.Success -> {
                    _stateGetCities.value = GetCityState.Display(result.data!!, loading = false)
                }
            }
        }
    }

    fun insertCity(city: CityDomainModel) {
        viewModelScope.launch {
            insertCityUseCase.execute(city)
        }
    }
}