package com.example.moviesapp.presentation.movies.view.list

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.model.entities.favourite.FavouriteMovie
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.repositories.MovieRepository
import com.example.moviesapp.presentation.movies.viewmodel.MoviesListViewModel
import com.example.moviesapp.presentation.movies.utils.imageOptionMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieViewHolder(
    itemView: View,
    val listener: MoviesAdapter.OnClickPoster?,
    val viewModel: MoviesListViewModel,
    val repository: MovieRepository
) :
    RecyclerView.ViewHolder(itemView) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val configuration = viewModel.configuration
    private val genres = viewModel.genreList

    private val pgMovie = itemView.findViewById<AppCompatTextView>(R.id.pg_movie)
    private val favouriteImage = itemView.findViewById<AppCompatImageView>(R.id.favourite_image)
    private val imagePoster = itemView.findViewById<AppCompatImageView>(R.id.movie_poster)
    private val tagLine = itemView.findViewById<AppCompatTextView>(R.id.tag_line_movie)
    private val countReviews = itemView.findViewById<AppCompatTextView>(R.id.reviews_count_movie)
    private val title = itemView.findViewById<AppCompatTextView>(R.id.title_movie)

    //private val timeLine = itemView.findViewById<AppCompatTextView>(R.id.time_film)
    private val listStarsRating = listOf<AppCompatImageView>(
        itemView.findViewById(R.id.star_one_movie),
        itemView.findViewById(R.id.star_two_movie),
        itemView.findViewById(R.id.star_three_movie),
        itemView.findViewById(R.id.star_four_movie),
        itemView.findViewById(R.id.star_five_movie)
    )

    @SuppressLint("SetTextI18n")
    fun onBind(movie: Result) {
        setClickListener(movie = movie)
        title.text = movie.title
        pgMovie.text = if (movie.adult == true) "+18" else "+16"
        tagLine.text = genres?.genres?.filter { movie.genreIDS?.contains(it.id) ?: false }
            ?.joinToString(separator = " , ") { it.name ?: "" }

        countReviews.text = "${movie.voteCount} reviews"
        //timeLine.text = "${movie.runtime} MIN"

        downloadImage(movie = movie)
        bindStars(countRating = (movie.voteAverage?.div(2))?.toInt() ?: 0)


        bindFavouriteImage(isFavourite = movie.id)
    }

    private fun setClickListener(movie: Result) {
        itemView.apply {
            setOnClickListener {
                listener?.createMoviesDetailsFragment(
                    movieId = movie.id,
                    configuration = viewModel.configuration
                )
            }
        }
        favouriteImage.apply {
            setOnClickListener {
                scope.launch {
                    val favouriteMovie = repository.getFavouriteMovieById(id = movie.id)
                    if (favouriteMovie != null) {
                        repository.deleteFavouriteMovieById(id = movie.id)
                        paintLike(condition = false)
                    } else {
                        repository.insertFavouriteMovie(favouriteMovie = FavouriteMovie(id = movie.id))
                        paintLike(condition = true)
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
                paintLike(condition = true)
            }

        }

    }

    private fun paintLike(condition: Boolean) {
        if (condition) {
            favouriteImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like_positive
                )
            )
        } else {
            favouriteImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_like
                )
            )
        }
    }

    private fun downloadImage(movie: Result) {
        Glide.with(context)
            .clear(imagePoster)

        Glide.with(context)
            .load(
                configuration?.baseURL + (configuration?.posterSizes?.get(4)
                    ?: "") + movie.posterPath
            )
            .apply(imageOptionMovie)
            .into(imagePoster)
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context