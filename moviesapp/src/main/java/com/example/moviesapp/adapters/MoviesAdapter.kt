package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.fragments.list.Counter
import com.example.moviesapp.fragments.list.MovieDataType
import com.example.moviesapp.fragments.list.MoviesListViewModel
import com.example.moviesapp.pojo.configuration.Images

import com.example.moviesapp.pojo.movies.popular.Result
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter(
    val viewModel: MoviesListViewModel,
    var type: MovieDataType,
    var query: String? = null
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies = mutableListOf<Result>()

    interface OnClickPoster {
        fun createMoviesDetailsFragment(movieId: Long, configuration: Images?)
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false),
            listener = onClickPoster,
            movies = movies,
            viewModel = viewModel
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movie = movies[position])
        if (position >= movies.size.minus(4) && movies.size >= 20) {
            if (query == null) {
                viewModel.loadMovies(type = type)
            } else {
                viewModel.loadMovies(type = type, query = query)
            }
        }
    }


    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Result>) {
        if (Counter.count != 0 && Counter.count != 1) {
            val oldMovies = movies.subList(movies.size - 20, movies.size)
            if (oldMovies != newMovies) movies.addAll(newMovies)
        } else if (Counter.count == 1) {
            movies = newMovies as MutableList<Result>
        } else {
            movies.addAll(newMovies)
        }

    }

    fun clearMovies() {
        movies = mutableListOf()
    }

}
