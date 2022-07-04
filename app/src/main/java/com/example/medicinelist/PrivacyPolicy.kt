package com.example.medicinelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.medicinelist.databinding.ActivityPrivacyProfileBinding

class PrivacyPolicy : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

    }
}