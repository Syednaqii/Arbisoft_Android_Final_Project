package com.example.movieflix.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.models.ApiCalls
import com.example.movieflix.models.ApiResponse
import com.example.movieflix.models.DetailMovieApIInterface
import com.example.movieflix.models.DetailMovieApiClient
import com.example.movieflix.models.Movie
import com.example.movieflix.models.PopularMovieAPIInterface
import com.example.movieflix.models.PopularMovieApiClient
import kotlinx.coroutines.launch

class MyViewModel : ViewModel()
{
    private var _movieData = MutableLiveData<Movie?>()
    val movieData: LiveData<Movie?> = _movieData

    private var _movieDataList = MutableLiveData<List<Movie>>()
    val movieDataList: LiveData<List<Movie>> = _movieDataList

    private var _popMovieIdData = MutableLiveData<Int>()
    val popMovieIdData: MutableLiveData<Int> = _popMovieIdData

    private val apiCalls : ApiCalls = ApiCalls()

    fun setPopMovieId(id:Int)
    {
        _popMovieIdData.value=id
    }
    
    fun setDataList(moviesList:List<Movie>)
    {
        _movieDataList.value = moviesList
    }

    fun popularMoviesApiCall()
    {
        val apiClient = PopularMovieApiClient()
        val apiService: PopularMovieAPIInterface = apiClient.getApiService()
        viewModelScope.launch {
            val data: ApiResponse? = apiCalls.apiCallPopular(apiService)
            val popularMovies : List<Movie> = data?.apiInfo ?: emptyList()
            _movieDataList.value=popularMovies
        }
    }

    fun detailMoviesApiCall(movieId : Int )
    {
        val apiClient = DetailMovieApiClient()
        val apiService: DetailMovieApIInterface = apiClient.getApiServiceDetail()
        viewModelScope.launch {
            val movieDetail: Movie? = apiCalls.apiCallDetail(apiService,movieId)
            _movieData.value=movieDetail
        }
    }
}

