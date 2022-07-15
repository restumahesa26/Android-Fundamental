package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        displaResult()

        activityMainBinding.btnCalculate.setOnClickListener {
            val width = activityMainBinding.edtWidth.text.toString()
            val height = activityMainBinding.edtHeight.text.toString()
            val length = activityMainBinding.edtLength.text.toString()

            when {
                width.isEmpty() -> {
                    activityMainBinding.edtWidth.error = "Masih Kosong"
                }
                height.isEmpty() -> {
                    activityMainBinding.edtHeight.error = "Masih Kosong"
                }
                length.isEmpty() -> {
                    activityMainBinding.edtLength.error = "Masih Kosong"
                }
                else -> {
                    viewModel.calculate(width, height, length)
                    displaResult()
                }
            }
        }
    }

    private fun displaResult() {
        activityMainBinding.tvResult.text = viewModel.result.toString()
    }
}