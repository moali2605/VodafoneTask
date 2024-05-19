package com.example.days_forecast

sealed class DaysForecastIntent {
    data object Load : DaysForecastIntent()
}