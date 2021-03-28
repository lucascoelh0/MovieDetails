package com.example.moviedetails.service

import com.example.moviedetails.model.Movie
import com.example.moviedetails.model.SimilarMoviesList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val MOVIE_ID = "458576"

interface Repository {

    @GET("movie/$MOVIE_ID")
    suspend fun getMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Movie

    @GET("movie/$MOVIE_ID/similar")
    suspend fun getSimilarMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): SimilarMoviesList
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var repository: Repository = retrofit.create(Repository::class.java)

