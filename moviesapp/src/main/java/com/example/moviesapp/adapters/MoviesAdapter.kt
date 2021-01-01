package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.application.MovieApp
import com.example.moviesapp.pojo.configuration.GenreList
import com.example.moviesapp.pojo.configuration.Images
import com.example.moviesapp.pojo.movies.popular.Result
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter(val configuration : Images , val genres : GenreList) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Result> = mutableListOf()

    interface OnClickPoster {
        fun createMoviesDetailsFragment(movie: Result)
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MovieViewHolder {
        return MovieViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false),
            listener = onClickPoster,
            movies = movies,
            configuration = configuration,
            genres = genres
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.onBind(movie = movies[position])

    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Result>) {
        movies = newMovies
    }
}