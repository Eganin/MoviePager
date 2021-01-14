package com.example.moviesapp.presentation.movies.view.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.model.entities.configuration.Images
import com.example.moviesapp.model.entities.movies.credits.Cast


class ActorsAdapter(val configuration: Images) : RecyclerView.Adapter<ActorViewHolder>() {


    private var actors = listOf<Cast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actors, parent, false),
            configuration = configuration
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actor = actors[position])
    }

    override fun getItemCount(): Int = actors.size


    fun bindActors(newActors: List<Cast>) {
        actors = newActors
    }

}
