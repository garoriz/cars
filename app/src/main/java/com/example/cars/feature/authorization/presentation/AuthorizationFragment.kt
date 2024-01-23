package com.example.cars.feature.authorization.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.cars.R
import com.example.cars.databinding.FragmentAuthorizationBinding
import com.google.android.material.snackbar.Snackbar

private const val LOGIN = "admin"
private const val PASSWORD = "admin"

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentAuthorizationBinding

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

        binding = FragmentAuthorizationBinding.bind(view)


        with(binding) {
            //при нажатии перейти назад на экран профиля или вывести snackbar о неправильных данных
            btnLogin.setOnClickListener {
                if (tilLogin.editText?.text.toString() == LOGIN &&
                    tilPassword.editText?.text.toString() == PASSWORD
                ) {
                    // запись в Shared Preferences, что пользователь авторизован
                    with(sharedPreferences.edit()) {
                        putBoolean(getString(R.string.is_login), true)
                        apply()
                    }
                    view.findNavController()
                        .navigate(
                            R.id.action_authorizationFragment_to_profileFragment,
                            null,
                            NavOptions.Builder().setPopUpTo(R.id.authorizationFragment, true)
                                .build()
                        )
                } else
                    showMessage(R.string.incorrect_login_and_or_password)
            }
        }
    }

    //показ Snackbara'a
    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}