package com.example.moviesapp.ui.presentation.movies.view.list

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ViewHolderMovieBinding
import com.example.moviesapp.model.entities.favourite.FavouriteMovie
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.ui.presentation.movies.viewmodel.MoviesListViewModel
import com.example.moviesapp.presentation.movies.utils.imageOptionMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieViewHolder(
    private val itemBinding: ViewHolderMovieBinding,
    private val listener: MoviesAdapter.OnClickPoster?,
    private val viewModel: MoviesListViewModel,
    private val repository: MovieRepository
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val configuration = viewModel.configuration
    private val genres = viewModel.genreList

    private val listStarsRating = listOf(
        itemBinding.starOneMovie as AppCompatImageView,
        itemBinding.starTwoMovie as AppCompatImageView,
        itemBinding.starThreeMovie as AppCompatImageView,
        itemBinding.starFourMovie as AppCompatImageView,
        itemBinding.starFiveMovie as AppCompatImageView,
    )

    @SuppressLint("SetTextI18n")
    fun onBind(movie: Result) {
        setClickListener(movie = movie)
        itemBinding.titleMovie.text = movie.title
        itemBinding.pgMovie.text = if (movie.adult == true) "+18" else "+16"
        itemBinding.tagLineMovie.text = genres?.genres?.filter { movie.genreIDS?.contains(it.id) ?: false }
            ?.joinToString(separator = " , ") { it.name ?: "" }

        itemBinding.reviewsCountMovie.text = "${movie.voteCount} reviews"

        downloadImage(movie = movie)
        bindStars(countRating = (movie.voteAverage?.div(2))?.toInt() ?: 0)


        bindFavouriteImage(isFavourite = movie.id)
    }

    private fun setClickListener(movie: Result) {
        itemBinding.apply {
            itemView.setOnClickListener {
                listener?.createMoviesDetailsFragment(
                    movieId = movie.id,
                    configuration = viewModel.configuration
                )
            }
        }
        itemBinding.favouriteImage.apply {
            setOnClickListener {
                scope.launch {
                    val favouriteMovie = repository.getFavouriteMovieById(id = movie.id)
                    if (favouriteMovie != null) {
                        repository.deleteFavouriteMovieById(id = movie.id)
                        uiScope.launch {
                            paintLike(condition = false)
                        }
                    } else {
                        repository.insertFavouriteMovie(favouriteMovie = FavouriteMovie(id = movie.id))
                        uiScope.launch {
                            paintLike(condition = true)
                        }

                    }
                }
            }
        }
    }

    private fun bindStars(countRating: Int) {
        for (i in 0 until countRating) {
            listStarsRating[i].setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_star_positive
                )
            )
        }
    }

    private fun bindFavouriteImage(isFavourite: Long) {
        scope.launch {
            val favouriteMovie = repository.getFavouriteMovieById(id = isFavourite)
            if (favouriteMovie != null) {
                uiScope.launch {
                    paintLike(condition = true)
                }
            } else {
                uiScope.launch {
                    paintLike(condition = false)
                }
            }

        }

    }

    private fun paintLike(condition: Boolean) {
        if (condition) {
            itemBinding.favouriteImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like_positive
                )
            )
        } else {
            itemBinding.favouriteImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like
                )
            )
        }
    }

    private fun downloadImage(movie: Result) {
        Glide.with(context)
            .clear(itemBinding.moviePoster)

        Glide.with(context)
            .load(
                configuration?.baseURL + (configuration?.posterSizes?.get(4)
                    ?: "") + movie.posterPath
            )
            .apply(imageOptionMovie)
            .into(itemBinding.moviePoster)
    }

}

private val RecyclerView.ViewHolder.context
    get() = itemView.context