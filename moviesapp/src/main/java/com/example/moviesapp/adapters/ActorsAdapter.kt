package com.example.moviesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.pojo.configuration.Images
import com.example.moviesapp.pojo.movies.credits.Cast
import com.example.moviesapp.viewholders.ActorViewHolder

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
