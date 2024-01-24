package com.example.cars.feature.addcar.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cars.R
import com.example.cars.databinding.FragmentAddCarBinding
import com.example.cars.db.AppDatabase
import com.example.cars.db.Car
import com.example.cars.db.gearboxes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException


class AddCarFragment : Fragment(R.layout.fragment_add_car) {
    //объявление binding'а для связи с xml
    private lateinit var binding: FragmentAddCarBinding

    // получение uri фото, котрое выбрал пользователь на устройстве
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { uploadFile(uri) }
        }
    private lateinit var db: AppDatabase
    private var photo: ByteArray? = null

    private fun uploadFile(uri: Uri) {
        with(binding) {
            // конвертаци фото в байты
            var bitmap = context?.contentResolver?.openInputStream(uri).use { data ->
                BitmapFactory.decodeStream(data)
            }
            bitmap = getResizedBitmap(bitmap)
            photo = convertBitmapToByteArray(bitmap)

            // изменение текста c "Добавить фото" на "Фото добавлено"
            tvAddMedia.text = getString(R.string.photo_is_added)
        }
    }

    // проверка полей на пустоту и добавление фото
    private fun isEmptyEditTexts(): Boolean {
        with(binding) {
            if (photo?.isEmpty() == true || tilBrand.editText?.text.toString().trim().isEmpty() ||
                tilModel.editText?.text.toString().trim().isEmpty() ||
                tilCapacity.editText?.text.toString().trim().isEmpty() ||
                tilColor.editText?.text.toString().trim().isEmpty() ||
                tilPrice.editText?.text.toString().trim().isEmpty() ||
                tilGearbox.editText?.text.toString().trim().isEmpty() ||
                tilYear.editText?.text.toString().trim().isEmpty()
            )
                return true
        }
        return false
    }

    //логика после создания фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase(requireContext())
        binding = FragmentAddCarBinding.bind(view)


        with(binding) {
            tvAddMedia.setOnClickListener {
                selectImageFromGalleryResult.launch("image/*")
            }

            // добавление в поле "Коробка передач" список значений
            (tilGearbox.editText as? MaterialAutoCompleteTextView)
                ?.setSimpleItems(gearboxes)

            // при нажатии конпки добавления авто проверить на пустоту поля и добавление фото
            btnAdd.setOnClickListener {
                if (isEmptyEditTexts()) {
                    showMessage(R.string.not_all_data_is_filled_in)
                } else {
                    // вызов корутины для асинхронной записи в БД
                    CoroutineScope(IO).launch {
                        photo?.let { photo ->
                            Car(
                                tilBrand.editText?.text.toString().trim(),
                                tilModel.editText?.text.toString().trim(),
                                photo,
                                tilYear.editText?.text.toString().trim().toInt(),
                                tilGearbox.editText?.text.toString().trim(),
                                tilCapacity.editText?.text.toString().trim().toInt(),
                                tilColor.editText?.text.toString().trim(),
                                tilPrice.editText?.text.toString().trim().toInt(),
                            )
                        }?.let { car ->
                            db.carDao().save(
                                car
                            )
                        }
                        withContext(Dispatchers.Main) {
                            view.findNavController()
                                .navigate(R.id.action_addCarFragment_to_mainFragment)
                        }
                    }
                }
            }
        }
    }

    // изменение разрешение фото
    private fun getResizedBitmap(bm: Bitmap): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = 1000.toFloat() / width
        val scaleHeight = 800.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        return Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        return try {
            baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            baos.toByteArray()
        } finally {
            if (baos != null) {
                try {
                    baos.close()
                } catch (e: IOException) {
                    Log.e("Error", e.toString())
                }
            }
        }
    }

    //показ Snackbar'a
    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }

}