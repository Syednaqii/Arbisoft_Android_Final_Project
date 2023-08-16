package com.example.movieflix.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieAPIInterface
{
    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ApiResponse>
}

