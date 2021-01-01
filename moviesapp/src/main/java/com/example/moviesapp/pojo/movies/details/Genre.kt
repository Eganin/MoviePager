package com.example.moviesapp.pojo.movies.details
import kotlinx.serialization.*

@Serializable
data class Genre (
    val id: Long,
    val name: String
)