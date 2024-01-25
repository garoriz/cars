package com.example.cars.feature.main.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cars.ParamValues
import com.example.cars.R
import com.example.cars.databinding.FragmentMainBinding
import com.example.cars.db.AppDatabase
import com.example.cars.db.Car
import com.example.cars.feature.main.presentation.adapter.CarListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_NAME = "car_id"

class MainFragment : Fragment(R.layout.fragment_main) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentMainBinding

    //объявление Shared Preferences для хранения состояния "авторизован ли пользователь"
    private lateinit var sharedPreferences: SharedPreferences

    private var carListAdapter: CarListAdapter? = null
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //получение Shared Preferences
        sharedPreferences =
            requireActivity().getSharedPreferences(getString(R.string.shared_preferences_name), 0)
    }

    //логика после создания фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        //получение из Shared Preferences boolean значения "авторизован ли пользователь"
        val isLogin = sharedPreferences.getBoolean(getString(R.string.is_login), false)
        db = AppDatabase(requireContext())


        with(binding) {
            // при нажатии на машину происходит переход к экрану со всей информацией
            carListAdapter = CarListAdapter {
                // передача id машины на экран со всеми характеристиками машины
                view.findNavController()
                    .navigate(R.id.action_mainFragment_to_carFragment, bundleOf(ARG_NAME to it))
            }

            binding.cars.run {
                adapter = carListAdapter
            }

            submitCars()

            //при нажатии на кнопку "Параметры" происходит переход к соответствующему экрану
            cvParams.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_mainFragment_to_paramsFragment)
            }

            /*при нажатии на иконку профиля происходит переход к экрану авторизации или профиля в
            зависимости от того, авторизован ли пользователь*/
            ivAccount.setOnClickListener {
                if (isLogin)
                    view.findNavController()
                        .navigate(R.id.action_mainFragment_to_profileFragment)
                else
                    view.findNavController()
                        .navigate(R.id.action_mainFragment_to_authorizationFragment)
            }
        }
    }

    // получение все авто из БД и отправка в адаптер
    private fun submitCars() {
        CoroutineScope(Dispatchers.IO).launch {
            // получение из БД
            val cars = db.carDao().getAllByParams(
                "%${ParamValues.brand}%",
                "%${ParamValues.model}%",
                "%${ParamValues.year}%",
                "%${ParamValues.gearbox}%",
                "%${ParamValues.capacity}%",
                "%${ParamValues.color}%"
            ) as MutableList<Car>
            // сортировка машин по id, сначала новые
            cars.sortByDescending { it.id }
            withContext(Dispatchers.Main) {
                carListAdapter?.submitList(cars)
                with(binding) {
                    // убирается видимость загрузки
                    progressBar.isVisible = false
                }
            }
        }
    }

}