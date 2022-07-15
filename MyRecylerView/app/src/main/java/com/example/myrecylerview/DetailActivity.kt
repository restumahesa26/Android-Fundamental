package com.example.myrecylerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    private lateinit var tvDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvDetail = findViewById(R.id.tv_detail)

        val data = intent.getParcelableExtra<Hero>("DATA")

        tvDetail.text = data?.name.toString()
    }
}