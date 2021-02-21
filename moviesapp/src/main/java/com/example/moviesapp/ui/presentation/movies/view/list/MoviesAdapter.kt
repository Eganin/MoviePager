package com.example.moviesapp.ui.presentation.movies.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ViewHolderMovieBinding
import com.example.moviesapp.ui.presentation.movies.viewmodel.MovieDataType
import com.example.moviesapp.ui.presentation.movies.viewmodel.MoviesListViewModel
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.repositories.MovieRepository

import com.example.moviesapp.presentation.movies.utils.network.hasConnection


class MoviesAdapter(
    val viewModel: MoviesListViewModel,
    var type: MovieDataType,
    var query: String? = null,
    val repository: MovieRepository
) : RecyclerView.Adapter<MovieViewHolder>() {

    private var movies = mutableListOf<Result>()

    interface OnClickPoster {
        fun createMoviesDetailsFragment(movieId: Long, configuration: Images?)
    }

    var onClickPoster: OnClickPoster? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            itemBinding = ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener = onClickPoster,
            viewModel = viewModel,
            repository = repository
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(movie = movies[position])
        if (position >= movies.size.minus(4) && movies.size >= 20 &&
            hasConnection(context = holder.itemView.context)
        ) viewModel.loadMovies(type = type)

    }


    override fun getItemCount() = movies.size

    fun size() = itemCount

    fun bindMovies(newMovies: List<Result>) {
        try {
            val oldMovies = movies.subList(movies.size - 20, movies.size)
            if (oldMovies != newMovies) movies.addAll(newMovies)
        } catch (e: Exception) {
            e.printStackTrace()
            movies.addAll(newMovies)
        }


    }

    fun clearMovies() {
        movies = mutableListOf()
        notifyDataSetChanged()
    }

}
