package com.example.movieflix.models


import com.example.movieflix.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RemoteDataSource
{
    interface MovieService
    {
        @GET("popular")
        fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
        ): Call<ApiResponse>

        @GET("movie/{movie_id}")
        fun getDetailMovies(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String,
        ): Call<Movie>
    }

    suspend fun apiCallPopular(apiService: MovieService): ApiResponse?
    {
        return withContext(Dispatchers.IO) {
            val call: Call<ApiResponse> = apiService.getPopularMovies(
                Constants.API_KEY,
                Constants.LANGUAGE,
                Constants.PAGE)
            val response: Response<ApiResponse> = call.execute()
            if (response.isSuccessful)
            {
                response.body()
            }
            else
            {
                null
            }
        }
    }

    suspend fun apiCallDetail(apiService: MovieService,popMovieId:Int): Movie?
    {
        return withContext(Dispatchers.IO) {
            val call: Call<Movie> = apiService.getDetailMovies(popMovieId, Constants.API_KEY)
            val response: Response<Movie> = call.execute()
            if (response.isSuccessful)
            {
                response.body()
            }
            else
            {
                null
            }
        }
    }
}
