package com.example.data.mapper

import com.example.data.model.WeatherResponse
import com.example.domain.model.WeatherDomainModel


fun WeatherResponse.toWeatherDomainModel(): WeatherDomainModel = WeatherDomainModel(
    timezone = timezone,
    temperature = current.temperature,
    feelsLike = current.feelsLike,
    pressure = current.pressure,
    humidity = current.humidity,
    uvi = current.uvi,
    windSpeed = current.windSpeed,
    description = current.weather[0].description,
    iconSet = current.weather[0].icon,
    daily = daily.map { it.toWeatherDomainModelDaily() }
)

fun WeatherResponse.Daily.toWeatherDomainModelDaily(): WeatherDomainModel.DailyWeather = WeatherDomainModel.DailyWeather(
    temp = temperature.day,
    feelsLike = feelsLike.day,
    description = weather[0].description,
    dateTime = dateTime,
    iconSet = weather[0].icon

)
