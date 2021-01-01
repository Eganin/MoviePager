package com.example.moviesapp.pojo.movies.details

import kotlinx.serialization.*

@Serializable
data class ResponseMovieDetail (
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,

    @SerialName("imdb_id")
    val imdbID: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String,


    @SerialName("release_date")
    val releaseDate: String,

    val revenue: Long,
    val runtime: Long,


    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long
)