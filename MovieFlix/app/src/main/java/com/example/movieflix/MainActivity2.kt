package com.example.movieflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.adapters.MovieListAdapter
import com.example.movieflix.databinding.ActivityMain2Binding
import com.example.movieflix.models.ApiResponse
import com.example.movieflix.models.Movie
import com.example.movieflix.models.PopularMovieAPIInterface
import com.example.movieflix.models.PopularMovieApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    private var _binding: ActivityMain2Binding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)



    }
}