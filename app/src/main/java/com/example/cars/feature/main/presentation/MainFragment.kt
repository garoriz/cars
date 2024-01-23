package com.example.cars.feature.main.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cars.R
import com.example.cars.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentMainBinding

    //объявление Shared Preferences для хранения состояния "авторизован ли пользователь"
    private lateinit var sharedPreferences: SharedPreferences

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


        with(binding) {
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
}