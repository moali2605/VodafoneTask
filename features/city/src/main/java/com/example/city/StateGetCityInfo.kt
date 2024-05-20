package com.example.city

import com.example.domain.model.CityResponseDomainModel

sealed class StateGetCityInfo {
    data class Display(
        var cityInfoResponse: List<CityResponseDomainModel> =
            listOf(
                CityResponseDomainModel(
                    name = "", latitude = 0.0, longitude = 0.0, country = "", state = ""
                )
            ),
        val loading: Boolean = true
    ) : GetCityState()

    data class Failure(val errorMsg: String = "") : GetCityState()
}