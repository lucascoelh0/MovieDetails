package com.example.moviedetails.view.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedetails.databinding.ActivityMainBinding
import com.example.moviedetails.view.adapter.MovieAdapter
import com.example.moviedetails.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val model by viewModels<MainViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.initializeDB(this)
        model.getMovie()

        model.dbInitialized.observe(this, {
            model.popMapGenres()
        })

        model.movie.observe(this, {

            val movie = model.movie.value!!

            model.loadImage(binding.imagePoster, movie.poster_path)

            binding.apply {
                textTitle.text = movie.title
                textLikeCount.text = model.getFormatedLike(movie.vote_count, Locale.ENGLISH)
                textPopularity.text = movie.popularity.toInt().toString()
            }
        })

        model.mapGenres.observe(this, {
            model.mapGenres.value
            model.popListMovies()
        })

        model.listMovie.observe(this, {

            movieAdapter = model.listMovie.value?.let { MovieAdapter(it, model.mapGenres.value) }!!

            val recyclerView = binding.recyclerMovies
            recyclerView.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }

            binding.constraintProgress.visibility = View.GONE
            binding.constraintContent.visibility = View.VISIBLE
        })

        binding.imageHeart.setOnClickListener {
            model.likeClick(binding.imageHeart)
        }
    }
}