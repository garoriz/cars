package com.example.cars.feature.main.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cars.db.Car
import com.example.cars.feature.main.presentation.diffutils.CarDiffItemCallback

class CarListAdapter(
    private val action: (Int) -> Unit,
) : ListAdapter<Car, CarHolder>(CarDiffItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CarHolder = CarHolder.create(parent, action)

    override fun onBindViewHolder(holder: CarHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Car>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}