package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R

class MovieAdapter(var movies: MutableList<Int> = mutableListOf()) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface OnClickPoster {
        fun click(position: Int)
    }

    var onClickPoster: OnClickPoster? = null

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.apply {
                setOnClickListener { onClickPoster?.click(position = adapterPosition) }
            }
        }

        fun bind(position: Int) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(position = position)

    override fun getItemCount() = movies.size
}