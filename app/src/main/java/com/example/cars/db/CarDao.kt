package com.example.cars.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// интерфейс с функциями-операциями с БД
@Dao
interface CarDao {
    // достать все авто из БД по параметрам
    // поиск идёт не строго по строке, а по подстроке, т.е. если в параметре "Merc", то будут на
    // экране и марки Merc, и Mercedes, и даже AMErc (допустим таие марки существуют),
    // то же самое касается чисел
    @Query("SELECT * FROM cars WHERE brand like :brand AND model like :model AND year like :year AND gearbox like :gearbox AND capacity like :capacity AND color like :color")
    fun getAllByParams(
        brand: String,
        model: String,
        year: String,
        gearbox: String,
        capacity: String,
        color: String,
    ): List<Car>

    // записать авто в БД
    @Insert
    fun save(car: Car)

    @Query("SELECT * FROM cars WHERE id = :id")
    fun getCarById(id: Int): Car
}