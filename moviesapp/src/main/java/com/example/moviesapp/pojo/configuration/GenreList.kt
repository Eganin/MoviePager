package com.example.moviesapp.pojo.configuration

import com.example.moviesapp.pojo.movies.details.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreList (
    @SerialName("genres")
    val genres: List<Genre>
)