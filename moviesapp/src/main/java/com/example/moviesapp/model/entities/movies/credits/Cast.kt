package com.example.moviesapp.model.entities.movies.credits

import kotlinx.serialization.*

@Serializable
data class Cast(
    val adult: Boolean,
    val gender: Long,
    val id: Long,

    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long,

    val character: String,

    @SerialName("credit_id")
    val creditID: String,

    val order: Long
)