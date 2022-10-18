package com.example.and_diploma_shaba.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import com.example.and_diploma_shaba.R


@AndroidEntryPoint
class MapActivity : AppCompatActivity(R.layout.activity_map) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar((findViewById(R.id.toolbar)))

    }

}

