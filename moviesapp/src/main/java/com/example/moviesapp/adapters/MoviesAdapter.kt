package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: MutableList<Movie> = mutableListOf()

    interface OnClickPoster {
        fun click(position: Int)
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = onClickPoster
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.onBind(movie = movies[position])

    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies as MutableList<Movie>
    }

    fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
    }
}