package com.example.moviesapp.model.entities.movies.popular

import com.example.moviesapp.model.entities.movies.popular.results.Result
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovies (
    val page: Long,
    val results: List<Result>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)