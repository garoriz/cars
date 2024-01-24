package com.example.cars.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// описание модели машины для хранения в БД
@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val brand: String,
    val model: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val photo: ByteArray,
    val year: Int,
    val gearbox: String,
    val capacity: Int,
    val color: String,
    val price: Int,
) {
    @Ignore
    constructor(
        brand: String,
        model: String,
        photo: ByteArray,
        year: Int,
        gearbox: String,
        capacity: Int,
        color: String,
        price: Int,
    ) :
            this(0, brand, model, photo, year, gearbox, capacity, color, price)
}

val gearboxes = arrayOf("Механическая", "Автоматическая")
