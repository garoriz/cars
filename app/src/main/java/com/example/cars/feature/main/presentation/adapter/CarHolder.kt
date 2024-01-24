package com.example.cars.feature.main.presentation.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cars.databinding.ItemCarBinding
import com.example.cars.db.Car


class CarHolder(
    // binding для связи с xml
    private val binding: ItemCarBinding,
    // действие, котрое будет происходить при нажатии на карточку авто на главном экране
    // в данном случае происходит переход  полным параметрам авто
    private val action: (Car) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var car: Car? = null

    // связываание данных из БД с элементами XML
    fun bind(item: Car) {
        this.car = item
        with(binding) {
            // конвертация из байтов в фото
            val bmp = BitmapFactory.decodeByteArray(item.photo, 0, item.photo.size)
            ivCar.load(bmp)

            val carName = "${item.brand} ${item.model}, ${item.year}"
            tvCar.text = carName

            val year = "${item.price} Р"
            tvYear.text = year
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            action: (Car) -> Unit,
        ) = CarHolder(
            ItemCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), action
        )
    }
}