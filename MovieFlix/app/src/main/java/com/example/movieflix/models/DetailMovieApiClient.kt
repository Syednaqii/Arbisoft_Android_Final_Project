package com.example.movieflix.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailMovieApiClient
{
    private val baseURL = 	"https://api.themoviedb.org/3/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiServiceDetail(): DetailMovieApIInterface
    {
        return retrofit.create(DetailMovieApIInterface::class.java)
    }
}
