package com.example.moviedetails.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviedetails.R
import com.example.moviedetails.databinding.ItemMovieBinding
import com.example.moviedetails.model.SimilarMovie

class MovieAdapter(
    private val listMovies: List<SimilarMovie>,
    private val mapGenres: Map<Int, String>?
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

                textYear.text = movie.release_date?.take(4) ?: "TBA"

                val genresString =
                    movie.genre_ids
                        .map { "${mapGenres?.get(it)}" }
                        .toString()
                        .drop(1)
                        .dropLast(1)

                textGenres.text = genresString

                if (movie.poster_path != null) {
                    Glide.with(itemBinding.root.context)
                        .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                imagePoster.visibility = View.VISIBLE
                                progressPoster.visibility = View.GONE
                                return false
                            }

                        })
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