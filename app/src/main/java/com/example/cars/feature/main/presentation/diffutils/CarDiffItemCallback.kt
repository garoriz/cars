package com.example.cars.feature.main.presentation.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.cars.db.Car

// сравнение старого и нового списков машин для изменения определённых машин, а не всего списка
class CarDiffItemCallback : DiffUtil.ItemCallback<Car>() {
    // сравнение машин по id
    override fun areItemsTheSame(
        oldItem: Car,
        newItem: Car
    ): Boolean = oldItem.id == newItem.id

    // сравнение объектов Car целиком
    override fun areContentsTheSame(
        oldItem: Car,
        newItem: Car
    ): Boolean = oldItem == newItem
}