package com.example.moviesapp.model.entities.movies.popular.response

import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMoviesNowPlayong (
    val page: Long,
    val results: List<ResultNowPlayong>,

    @SerialName("total_pages")
    val totalPages: Long,

    @SerialName("total_results")
    val totalResults: Long
        )