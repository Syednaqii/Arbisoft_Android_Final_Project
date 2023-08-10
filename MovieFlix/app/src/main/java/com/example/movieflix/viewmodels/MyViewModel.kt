package com.example.movieflix.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieflix.models.ApiResponse
import com.example.movieflix.models.DetailMovieApIInterface
import com.example.movieflix.models.DetailMovieApiClient
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

class MyViewModel : ViewModel() {

    private var mov = MutableLiveData<Movie?>()
    val movieData: MutableLiveData<Movie?> get() = mov

    private var movList = MutableLiveData<List<Movie>>()
    val movieDataList: LiveData<List<Movie>> get() = movList

    private val coroutineScope: CoroutineScope = MainScope()

    fun setMovieData(movie: Movie)
    {
        mov.value=movie
    }

    fun setDataList(movieList:List<Movie>)
    {
        movList.value = movieList
    }

    fun popularMoviesApiCall()
    {
        val apiClient = PopularMovieApiClient()
        val apiService: PopularMovieAPIInterface = apiClient.getApiService()

        coroutineScope.launch {

            val data: ApiResponse? = apiCallPopular(apiService)
            val popularMovies : List<Movie> = data?.apiInfo ?: emptyList()
            movList.value=popularMovies
        }
    }
    fun detailMoviesApiCall(movieId : Int )
    {
        val apiClient = DetailMovieApiClient()
        val apiService: DetailMovieApIInterface = apiClient.getApiServiceDetail()

        coroutineScope.launch {

            val movieDetail: Movie? = apiCallDetail(apiService,movieId)
            mov.value=movieDetail
        }
    }
    private suspend fun apiCallPopular(apiService: PopularMovieAPIInterface): ApiResponse?
    {
        return withContext(Dispatchers.IO) {

            val apiKey = "e5f39fdedf71b1ca4720735fb0e5efae"
            val language = "en"
            val page=1
            val call: Call<ApiResponse> = apiService.getPopularMovies(apiKey,language,page)
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
    private suspend fun apiCallDetail(apiService: DetailMovieApIInterface,popMovieId:Int): Movie?
    {
        return withContext(Dispatchers.IO) {

            val apiKey = "e5f39fdedf71b1ca4720735fb0e5efae"
            val call: Call<Movie> = apiService.getDetailMovies(popMovieId,apiKey)
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