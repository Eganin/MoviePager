package com.example.moviesapp.model.entities.configuration

import com.example.moviesapp.model.entities.movies.details.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreList (
    @SerialName("genres")
    val genres: List<Genre>
)