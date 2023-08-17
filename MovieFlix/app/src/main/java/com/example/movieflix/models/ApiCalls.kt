package com.example.movieflix.models

import com.example.movieflix.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class ApiCalls
{
    suspend fun apiCallPopular(apiService: PopularMovieAPIInterface): ApiResponse?
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

    suspend fun apiCallDetail(apiService: DetailMovieApIInterface,popMovieId:Int): Movie?
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
