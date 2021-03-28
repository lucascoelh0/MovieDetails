package com.example.moviedetails.model

data class Movie(
    val genres: List<Genre>,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class SimilarMoviesList(
    val results: List<SimilarMovie>,
)

data class SimilarMovie(
    val genre_ids: List<Int>,
    val poster_path: String,
    val release_date: String,
    val title: String,
)