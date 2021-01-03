package com.example.moviesapp.viewholders

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.pojo.configuration.Images
import com.example.moviesapp.pojo.movies.credits.Cast
import com.example.moviesapp.utils.imageOption

class ActorViewHolder(itemView: View , val configuration: Images ) :
    RecyclerView.ViewHolder(itemView) {

    private val imageCast = itemView.findViewById<AppCompatImageView>(R.id.image_cast)
    private val name = itemView.findViewById<AppCompatTextView>(R.id.name_cast)

    fun onBind(actor: Cast) {
        Glide.with(context)
            .clear(imageCast)
        Glide.with(context)
            .load(configuration.baseURL + (configuration.logoSizes[3]) +actor.profilePath)
            .apply(imageOption)
            .into(imageCast)
        name.text = actor.name

    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context