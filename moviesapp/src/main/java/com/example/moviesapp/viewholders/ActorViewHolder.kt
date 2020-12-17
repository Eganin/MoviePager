package com.example.moviesapp.viewholders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R
import com.example.moviesapp.data.models.Actor

class ActorViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    companion object {
        val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_baseline_person_24)
            .fallback(R.drawable.ic_baseline_person_24)!!
    }

    private val imageCast = itemView.findViewById<AppCompatImageView>(R.id.image_cast)
    private val nameCast = itemView.findViewById<AppCompatTextView>(R.id.name_cast)

    fun onBind(actor: Actor) {
        Glide.with(context)
            .clear(imageCast)

        Glide.with(context)
            .load(actor.actorImage)
            .apply(imageOption)
            .into(imageCast)
        nameCast.text = actor.actorName
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context