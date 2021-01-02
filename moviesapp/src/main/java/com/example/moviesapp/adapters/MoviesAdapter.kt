package com.example.moviesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.fragments.list.MoviesListViewModel

import com.example.moviesapp.pojo.movies.popular.Result
import com.example.moviesapp.viewholders.MovieViewHolder

class MoviesAdapter(val viewModel : MoviesListViewModel) : RecyclerView.Adapter<MovieViewHolder>()  {

    private var movies = mutableListOf<Result>()
    private var isLoading  = true

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
            viewModel = viewModel
        )

    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int){
        holder.onBind(movie = movies[position])
        if(position >= movies.size.minus(1) && movies.size >= 20 && isLoading){
            Log.d("AAA",isLoading.toString())
            isLoading = false
            Log.d("AAA","DOWNLOADING")
            Counter.count++
            viewModel.loadMovies()
            isLoading = true
        }
    }


    override fun getItemCount() = movies.size

    fun bindMovies(newMovies: List<Result>) {
        movies.addAll(newMovies)
    }
}

object Counter{
    var count =1
}