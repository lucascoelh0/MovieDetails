package com.example.moviedetails.model

data class Results(
    val movies: List<Movie>
)

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
