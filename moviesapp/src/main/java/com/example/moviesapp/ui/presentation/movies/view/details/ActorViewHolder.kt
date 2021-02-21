package com.example.moviesapp.ui.presentation.movies.view.details

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.ViewHolderActorsBinding
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.credits.Cast
import com.example.moviesapp.presentation.movies.utils.imageOptionActor


class ActorViewHolder(private val itemBinding: ViewHolderActorsBinding , private val configuration: Images?) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(actor: Cast) {
        Glide.with(context)
            .clear(itemBinding.imageCast)
        Glide.with(context)
            .load(configuration?.baseURL + (configuration?.logoSizes?.get(3)) +actor.profilePath)
            .apply(imageOptionActor)
            .into(itemBinding.imageCast)
        itemBinding.nameCast.text = actor.name

    }

}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context