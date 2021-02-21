package com.example.moviesapp.ui.presentation.movies.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ViewHolderActorsBinding
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.credits.Cast


class ActorsAdapter(val configuration: Images? = null) : RecyclerView.Adapter<ActorViewHolder>() {


    private var actors = listOf<Cast?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            itemBinding = ViewHolderActorsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            configuration = configuration
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]
        if (actor != null) {
            holder.onBind(actor = actor)
        }

    }

    override fun getItemCount(): Int = actors.size


    fun bindActors(newActors: List<Cast>) {
        actors = newActors
    }

}
