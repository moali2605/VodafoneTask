package com.example.city

import com.example.myapplication.domain.model.CityDomainModel

sealed class GetCityState {
    data class Display(
        var cityResponse: List<CityDomainModel> =
            listOf(
                CityDomainModel
                    (
                    cityName = "", cityLat = 0.0, cityLong = 0.0
                )
            ),
        val loading: Boolean = true
    ) : GetCityState()

    data class Failure(val errorMsg: String = "") : GetCityState()
}