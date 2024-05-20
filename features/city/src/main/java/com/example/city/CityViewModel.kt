package com.example.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.DataHolder
import com.example.domain.usecase.GetAllCitiesUseCase
import com.example.domain.usecase.GetCityInfoUseCase
import com.example.domain.usecase.InsertCityUseCase
import com.example.myapplication.domain.model.CityDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCityInfoUseCase: GetCityInfoUseCase,
    private val getAllCitiesUseCase: GetAllCitiesUseCase,
    private val insertCityUseCase: InsertCityUseCase,
) : ViewModel() {

    private val _stateGetCityInfo: MutableStateFlow<StateGetCityInfo.Display> =
        MutableStateFlow(StateGetCityInfo.Display())
    val stateGetCityInfo = _stateGetCityInfo.asStateFlow()
    private val _getCityInfoErrorState: MutableSharedFlow<StateGetCityInfo.Failure> =
        MutableSharedFlow()
    val getCityInfoErrorState = _getCityInfoErrorState.asSharedFlow()

    private val _stateGetCities: MutableStateFlow<GetCityState.Display> =
        MutableStateFlow(GetCityState.Display())
    val stateGetCities = _stateGetCities.asStateFlow()
    private val _getCitiesErrorState: MutableSharedFlow<GetCityState.Failure> =
        MutableSharedFlow()
    val getCitiesErrorState = _getCitiesErrorState.asSharedFlow()

    fun getCityInfo(cityName: String) {
        viewModelScope.launch {
            when (val result = getCityInfoUseCase.execute(cityName)) {
                is DataHolder.Failure -> {
                    _getCityInfoErrorState.emit(StateGetCityInfo.Failure(result.error!!))
                }

                is DataHolder.Success -> {
                    _stateGetCityInfo.value =
                        StateGetCityInfo.Display(result.data!!, loading = false)
                }
            }
        }
    }

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