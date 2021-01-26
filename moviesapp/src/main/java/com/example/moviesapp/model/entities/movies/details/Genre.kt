package com.example.moviesapp.model.entities.movies.details
import kotlinx.serialization.*

@Serializable
data class Genre (
    val id: Long?=null,
    val name: String?=null
)