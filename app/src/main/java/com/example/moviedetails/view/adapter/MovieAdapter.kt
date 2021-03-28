package com.example.moviedetails.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedetails.databinding.ItemMovieBinding
import com.example.moviedetails.model.SimilarMovie

class MovieAdapter(private val listMovies: List<SimilarMovie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    inner class MovieViewHolder(private val itemBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: SimilarMovie) {
//            var genres = ""
//            if (movie.genres.isNotEmpty()) {
//                genres = movie.genres.map { "$it, " }.toString()
//                genres.dropLast(1)
//            }
//            movie.release_date.take(4)
            itemBinding.apply {
                textTitle.text = movie.title
                textYear.text =
                    if (movie.release_date.isNotEmpty()) movie.release_date.take(4) else "TBA"

//                textGenres.text = genres

                Glide.with(itemBinding.root.context)
                    .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                    .into(imagePoster)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount() = listMovies.size
}