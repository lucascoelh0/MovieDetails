package com.example.moviedetails.viewmodel

import android.content.Context
import android.icu.text.CompactDecimalFormat
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviedetails.BuildConfig
import com.example.moviedetails.R
import com.example.moviedetails.database.AppDatabase
import com.example.moviedetails.model.Genres
import com.example.moviedetails.model.Movie
import com.example.moviedetails.model.SimilarMovie
import com.example.moviedetails.service.*
import kotlinx.coroutines.launch
import java.util.*


class MainViewModel : ViewModel() {

    val API_KEY = BuildConfig.API_KEY
    val LANGUAGE = "PT-BR"
    val movie = MutableLiveData<Movie>()
    val listMovie = MutableLiveData<List<SimilarMovie>>()
    lateinit var mapGenres: Map<Int, String>
    var liked = false

    fun initializeDB(context: Context) {

        dbApp = AppDatabase.invoke(context)
        dbRepository = DBRepositoryImplementation(dbApp.genresDAO())

        viewModelScope.launch {
            if (dbRepository.isEmptyDBService() == null) {
                dbRepository.populateGenresDBService()
            }
        }
    }

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

    fun getFormatedLike(likeCount: Int, locale: Locale): String {
        val compactDecimalFormat =
            CompactDecimalFormat.getInstance(locale, CompactDecimalFormat.CompactStyle.SHORT)
        return compactDecimalFormat.format(likeCount)
    }

    fun changeLike(imageView: ImageView) {
        if (liked) {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        liked = !liked
    }

    fun popMapGenres() {
        viewModelScope.launch {
            val listGenres = dbRepository.getAllGenresDBService()
            mapGenres = listGenres.map {it.id to it.name}.toMap()
        }
    }
}