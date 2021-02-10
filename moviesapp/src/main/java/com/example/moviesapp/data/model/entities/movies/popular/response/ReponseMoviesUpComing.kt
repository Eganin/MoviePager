package com.example.moviesapp.model.entities.movies.popular.response

import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReponseMoviesUpComing (
    val page: Long,
    val results: List<ResultUpComing>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
)