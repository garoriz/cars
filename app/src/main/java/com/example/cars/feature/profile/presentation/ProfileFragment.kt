package com.example.cars.feature.profile.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cars.R
import com.example.cars.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentProfileBinding

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

        binding = FragmentProfileBinding.bind(view)

        with(binding) {

        }
    }
}