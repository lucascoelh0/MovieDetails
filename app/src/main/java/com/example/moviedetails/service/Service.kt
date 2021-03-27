package com.example.moviedetails.service

import com.example.moviedetails.model.Movie
import com.example.moviedetails.model.Results
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Repository {

    @GET("/movie/458576")
    suspend fun getMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Movie

    @GET("/movie/458576/similar")
    suspend fun getSimilarMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Results
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var repository: Repository = retrofit.create(Repository::class.java)

