package com.example.movieflix.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double,
)

data class ApiResponse(
    @SerializedName("page") val page:Int,
    @SerializedName("results") val apiInfo : ArrayList<Movie>
)





