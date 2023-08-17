package com.example.movieflix.dependencies


import com.example.movieflix.models.ApiCalls
import com.example.movieflix.models.DetailMovieApIInterface
import com.example.movieflix.models.DetailMovieApiClient
import com.example.movieflix.models.PopularMovieAPIInterface
import com.example.movieflix.models.PopularMovieApiClient

class DependencyContainer
{
    //Popular Movies Api
    private val apiClientPop = PopularMovieApiClient()
    val apiServicePop: PopularMovieAPIInterface = apiClientPop.getApiService()
    //Detail Movie Api
    private val apiClientDet = DetailMovieApiClient()
    val apiServiceDet : DetailMovieApIInterface = apiClientDet.getApiServiceDetail()
    val apiCalls : ApiCalls = ApiCalls()
}
