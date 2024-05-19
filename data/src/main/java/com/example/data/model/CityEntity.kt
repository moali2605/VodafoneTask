package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val cityName: String,
    val cityLat: Double,
    val cityLong: Double
)