package com.example.publicandroidhello

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.publicandroidhello.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tapButton.setOnClickListener {
            count++
            binding.counterText.text = getString(R.string.click_count, count)
        }
    }
}
