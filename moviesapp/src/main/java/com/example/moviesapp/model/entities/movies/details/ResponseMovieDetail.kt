package com.example.moviesapp.model.entities.movies.details


import kotlinx.serialization.*

@Serializable
data class ResponseMovieDetail (
    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,

    @SerialName("imdb_id")
    val imdbID: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,


    @SerialName("release_date")
    val releaseDate: String? = null,

    val revenue: Long? = null,
    val runtime: Long? = null,


    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null
)