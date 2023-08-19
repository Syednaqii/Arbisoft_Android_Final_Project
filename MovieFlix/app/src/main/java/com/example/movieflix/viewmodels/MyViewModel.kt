package com.example.movieflix.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.dependencies.DependencyContainer
import com.example.movieflix.models.ApiResponse
import com.example.movieflix.models.Movie
import kotlinx.coroutines.launch

class MyViewModel : ViewModel()
{
    private var _movieData = MutableLiveData<Movie?>()
    val movieData: LiveData<Movie?> = _movieData

    private var _movieDataList = MutableLiveData<List<Movie>>()
    val movieDataList: LiveData<List<Movie>> = _movieDataList

    private var _popMovieIdData = MutableLiveData<Int>()
    val popMovieIdData: MutableLiveData<Int> = _popMovieIdData

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
        val dependency = DependencyContainer()
        viewModelScope.launch {
            val data: ApiResponse? = dependency.remoteDataSource.apiCallPopular(dependency.interfacePop)
            val popularMovies : List<Movie> = data?.apiInfo ?: emptyList()
            _movieDataList.value=popularMovies
        }
    }

    fun detailMoviesApiCall(movieId : Int )
    {
        val dependency = DependencyContainer()
        viewModelScope.launch {
            val movieDetail: Movie? = dependency.remoteDataSource.apiCallDetail(dependency.interfaceDet,movieId)
            _movieData.value=movieDetail
        }
    }
}
