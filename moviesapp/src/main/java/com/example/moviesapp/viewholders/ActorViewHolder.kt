package com.example.moviesapp.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.utils.imageOption

class ActorViewHolder(itemView: View ) :
    RecyclerView.ViewHolder(itemView) {

    private val imageCast = itemView.findViewById<AppCompatImageView>(R.id.image_cast)
    private val name = itemView.findViewById<AppCompatTextView>(R.id.name_cast)

    /*
    fun onBind(actor: Actor) {
        Glide.with(context)
            .clear(imageCast)

        Glide.with(context)
            .load(actor.picture)
            .apply(imageOption)
            .into(imageCast)
        name.text = actor.name

    }

     */
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context