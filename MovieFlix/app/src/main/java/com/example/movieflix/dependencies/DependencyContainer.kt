package com.example.movieflix.dependencies

import com.example.movieflix.models.RemoteDataSource
import com.example.movieflix.models.RetrofitServices
import com.example.movieflix.utils.Constants

class DependencyContainer {
    private val retrofitPopular = RetrofitServices(Constants.POP_API)
    val interfacePop: RemoteDataSource.MovieService = retrofitPopular.getApiService()

    private val retrofitDetailed = RetrofitServices(Constants.DETAIL_API)
    val interfaceDet: RemoteDataSource.MovieService = retrofitDetailed.getApiService()

    val remoteDataSource = RemoteDataSource()
}
