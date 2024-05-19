package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM CityEntity")
    fun getAllCities(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityEntity)
}