package com.example.cars.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// интерфейс с функциями-операциями с БД
@Dao
interface CarDao {
    // достать все авто из БД
    @Query("SELECT * FROM cars WHERE brand like :brand")
    fun getAll(brand: String): List<Car>

    // записать авто в БД
    @Insert
    fun save(car: Car)
}