package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.fragments.list.MoviesListViewModel
import com.example.moviesapp.pojo.configuration.Images

import com.example.moviesapp.pojo.movies.popular.Result
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter(val viewModel : MoviesListViewModel) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies = mutableListOf<Result>()

    interface OnClickPoster {
        fun createMoviesDetailsFragment(movieId : Long , configuration : Images? )
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MovieViewHolder {
        return MovieViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false),
            listener = onClickPoster,
            movies = movies,
            viewModel = viewModel
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int){
        holder.onBind(movie = movies[position])
        if(position >= movies.size.minus(4) && movies.size >= 20){
            viewModel.loadMovies()
        }
    }


    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Result>) {
        movies.addAll(newMovies)
    }

}
