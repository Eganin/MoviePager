package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.models.Movie
import com.example.moviesapp.databinding.ViewHolderMovieBinding
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = mutableListOf()

    interface OnClickPoster {
        fun createMoviesDetailsFragment(movie: Movie)
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MovieViewHolder {
        return MovieViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false),
            listener = onClickPoster,
            movies = movies
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.onBind(movie = movies[position])

    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Movie>) {
        movies = newMovies
    }
}