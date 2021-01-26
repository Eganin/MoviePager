package com.example.moviesapp.model.entities.movies.credits

import kotlinx.serialization.*

@Serializable
data class Cast(
    val adult: Boolean?=null,
    val gender: Long?=null,
    val id: Long?=null,

    val name: String?=null,

    @SerialName("original_name")
    val originalName: String?=null,

    val popularity: Double?=null,

    @SerialName("profile_path")
    val profilePath: String?=null,

    @SerialName("cast_id")
    val castID: Long?=null,

    @SerialName("character")
    val character: String?=null,

    @SerialName("credit_id")
    val creditID: String?=null,

    val order: Long?=null
)