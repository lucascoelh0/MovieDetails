package com.example.moviedetails.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviedetails.BuildConfig
import com.example.moviedetails.model.Movie
import com.example.moviedetails.model.SimilarMovie
import com.example.moviedetails.service.Repository
import com.example.moviedetails.service.repository
import kotlinx.coroutines.launch

class MainViewModel(repository: Repository) : ViewModel() {

    val API_KEY = BuildConfig.API_KEY
    val LANGUAGE = "PT-BR"
    val movie = MutableLiveData<Movie>()
    val listMovie = MutableLiveData<List<SimilarMovie>>()

    fun getMovie() {
        viewModelScope.launch {
            movie.value = repository.getMovie(
                API_KEY,
                LANGUAGE
            )
        }
    }

    fun popListMovies() {
        viewModelScope.launch {
            listMovie.value = repository.getSimilarMovies(
                API_KEY,
                LANGUAGE
            ).results
        }
    }

    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView)
            .load("https://image.tmdb.org/t/p/w500/$url")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}