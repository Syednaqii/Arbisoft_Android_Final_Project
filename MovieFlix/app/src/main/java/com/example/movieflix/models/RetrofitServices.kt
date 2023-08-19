package com.example.movieflix.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServices(url: String) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): RemoteDataSource.MovieService {
        return retrofit.create(RemoteDataSource.MovieService::class.java)
    }
}
