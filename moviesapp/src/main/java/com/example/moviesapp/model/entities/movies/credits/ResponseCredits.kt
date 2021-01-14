package com.example.moviesapp.model.entities.movies.credits

import kotlinx.serialization.*
@Serializable
data class ResponseCredits (
    val id: Long,
    val cast: List<Cast>
)
