package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.models.Actor
import com.example.moviesapp.viewholders.ActorViewHolder

class ActorsAdapter : RecyclerView.Adapter<ActorViewHolder>() {

    private var actors = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actors, parent, false)
        )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) =
        holder.onBind(actor = actors[position])

    override fun getItemCount(): Int = actors.size

    fun bindActors(newActors: List<Actor>) {
        actors = newActors
    }
}
