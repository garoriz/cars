package com.example.cars.feature.car.presentation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.example.cars.R
import com.example.cars.databinding.FragmentCarBinding
import com.example.cars.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_NAME = "car_id"

class CarFragment : Fragment(R.layout.fragment_car) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentCarBinding

    private lateinit var db: AppDatabase

    //логика после создания фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCarBinding.bind(view)
        db = AppDatabase(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            // получение из БД машины по id (id был передан на главном экране при переходе сюда)
            val car = arguments?.getInt(ARG_NAME)?.let { db.carDao().getCarById(it) }
            withContext(Dispatchers.Main) {
                with(binding) {
                    val carName = "${car?.brand} ${car?.model}, ${car?.year}"
                    tvCar.text = carName

                    // конвертация из байтов в фото
                    val bmp =
                        car?.photo?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                    ivCar.load(bmp)

                    tvGearboxValue.text = car?.gearbox

                    val capacityString = "${car?.capacity} л.с."
                    tvCapacityValue.text = capacityString

                    tvColorValue.text = car?.color

                    val priceString = "${car?.price} Р"
                    tvPriceValue.text = priceString

                    // убирается видимость загрузки
                    progressBar.isVisible = false
                }
            }
        }
    }
}