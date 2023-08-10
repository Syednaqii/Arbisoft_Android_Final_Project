package com.example.movieflix.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PopularMovieApiClient
{
    private val baseURL = 	"https://api.themoviedb.org/3/movie/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): PopularMovieAPIInterface {
        return retrofit.create(PopularMovieAPIInterface::class.java)
    }
}

