package com.example.movieflix.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieflix.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity()
{
    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}

