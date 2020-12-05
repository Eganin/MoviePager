package com.example.moviesapp.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.adapters.MoviesAdapter
import com.example.moviesapp.data.models.Movie

class MovieViewHolder(itemView: View, listener: MoviesAdapter.OnClickPoster?) :
    RecyclerView.ViewHolder(itemView) {

    private val pgMovie = itemView.findViewById<AppCompatTextView>(R.id.pg_movie)
    private val favouriteImage = itemView.findViewById<AppCompatImageView>(R.id.favourite_image)
    private val imagePoster = itemView.findViewById<AppCompatImageView>(R.id.movie_poster)
    private val tagLine = itemView.findViewById<AppCompatTextView>(R.id.tag_line_movie)
    private val countReviews = itemView.findViewById<AppCompatTextView>(R.id.reviews_count_movie)
    private val title = itemView.findViewById<AppCompatTextView>(R.id.title_movie)
    private val timeLine = itemView.findViewById<AppCompatTextView>(R.id.time_film)
    private val listStarsRating = listOf<AppCompatImageView>(
        itemView.findViewById(R.id.star_one_movie),
        itemView.findViewById(R.id.star_two_movie),
        itemView.findViewById(R.id.star_three_movie),
        itemView.findViewById(R.id.star_four_movie),
        itemView.findViewById(R.id.star_five_movie)
    )

    init {
        itemView.apply {
            setOnClickListener { listener?.click(position = adapterPosition) }
        }
    }

    fun onBind(movie: Movie) {

        title.text = movie.title
        pgMovie.text = movie.pg
        tagLine.text = movie.tags.joinToString(separator = ",")
        "${movie.countReviews} reviews".also { countReviews.text = it }
        "${movie.timeLine} MIN".also { timeLine.text = it }
        Glide.with(context)
            .load(movie.imageMovie)
            .apply(ActorViewHolder.imageOption)
            .into(imagePoster)
        bindStars(countRating = movie.starRating)
        bindFavouriteImage(isFavourite = movie.isFavourite)

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

    private fun bindFavouriteImage(isFavourite: Boolean) {
        if (isFavourite) favouriteImage.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_like_positive
            )
        ) else favouriteImage.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_like
            )
        )
    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context