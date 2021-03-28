package com.example.moviedetails.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedetails.R
import com.example.moviedetails.databinding.ItemMovieBinding
import com.example.moviedetails.model.SimilarMovie

class MovieAdapter(
    private val listMovies: List<SimilarMovie>,
    private val mapGenres: Map<Int, String>
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    inner class MovieViewHolder(private val itemBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: SimilarMovie) {
            itemBinding.apply {
                textTitle.text = movie.title
                textYear.text =
                    if (movie.release_date.isNotEmpty()) movie.release_date.take(4) else "TBA"

                val genresString =
                    movie.genre_ids
                        .map { "${mapGenres[it]}" }
                        .toString()
                        .drop(1)
                        .dropLast(1)

                textGenres.text = genresString

                if (movie.poster_path != null) {
                    Glide.with(itemBinding.root.context)
                        .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                        .into(imagePoster)
                } else {
                    imagePoster.setImageResource(R.drawable.no_image)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount() = listMovies.size
}