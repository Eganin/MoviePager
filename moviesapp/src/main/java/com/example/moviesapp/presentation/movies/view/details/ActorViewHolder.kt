package com.example.moviesapp.presentation.movies.view.details

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.credits.Cast
import com.example.moviesapp.presentation.movies.utils.imageOptionActor


class ActorViewHolder(itemView: View , val configuration: Images) :
    RecyclerView.ViewHolder(itemView) {

    private val imageCast = itemView.findViewById<AppCompatImageView>(R.id.image_cast)
    private val name = itemView.findViewById<AppCompatTextView>(R.id.name_cast)

    fun onBind(actor: Cast) {
        Glide.with(context)
            .clear(imageCast)
        Glide.with(context)
            .load(configuration.baseURL + (configuration.logoSizes[3]) +actor.profilePath)
            .apply(imageOptionActor)
            .into(imageCast)
        name.text = actor.name

    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context