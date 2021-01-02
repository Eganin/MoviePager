package com.example.moviesapp.pojo.movies.credits
import kotlinx.serialization.*
@Serializable
data class ResponseCredits (
    val id: Long,
    val cast: List<Cast>
)
