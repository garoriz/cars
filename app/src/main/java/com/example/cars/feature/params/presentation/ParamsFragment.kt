package com.example.cars.feature.params.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cars.ParamValues
import com.example.cars.R
import com.example.cars.databinding.FragmentParamsBinding
import com.example.cars.db.gearboxes
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class ParamsFragment : Fragment(R.layout.fragment_params) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentParamsBinding

    //логика после создания фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentParamsBinding.bind(view)


        with(binding) {
            // добавление списка в поле "Коробка передач"
            (tilGearbox.editText as? MaterialAutoCompleteTextView)
                ?.setSimpleItems(gearboxes + getString(R.string.any))

            // при нажатии на поиск происходит запись параметров и переход к главному экрану
            btnSearch.setOnClickListener {
                ParamValues.brand = tilBrand.editText?.text.toString().trim()
                ParamValues.model = tilModel.editText?.text.toString().trim()
                ParamValues.year = tilYear.editText?.text.toString().trim()
                ParamValues.gearbox = tilGearbox.editText?.text.toString().trim()
                ParamValues.capacity = tilCapacity.editText?.text.toString().trim()
                ParamValues.color = tilColor.editText?.text.toString().trim()
                view.findNavController().popBackStack()
            }
        }
    }
}