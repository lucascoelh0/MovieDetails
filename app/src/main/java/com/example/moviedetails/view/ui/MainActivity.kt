package com.example.moviedetails.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedetails.databinding.ActivityMainBinding
import com.example.moviedetails.service.repository
import com.example.moviedetails.view.adapter.MovieAdapter
import com.example.moviedetails.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val model by viewModels<MainViewModel>() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getMovie()
        model.popListMovies()

        model.movie.observe(this, {

            val movie = model.movie.value

            model.loadImage(binding.imagePoster, movie?.poster_path)

            binding.apply {
                textTitle.text = movie?.title
                textPopularity.text = movie?.popularity.toString()
            }
        })

        model.listMovie.observe(this, {
            movieAdapter = model.listMovie.value?.let { MovieAdapter(it) }!!

            val recyclerView = binding.recyclerMovies
            recyclerView.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        })
    }
}