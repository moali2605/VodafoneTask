package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CityDataBase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}