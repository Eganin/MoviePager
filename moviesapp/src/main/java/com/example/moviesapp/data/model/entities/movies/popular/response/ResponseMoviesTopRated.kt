package com.example.moviesapp.model.entities.movies.popular.response

import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMoviesTopRated (
    val page: Long,
    val results: List<ResultTopRated>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)