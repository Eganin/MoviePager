package com.example.moviesapp.data.models

data class Movie(
    val id: Int,
    val title: String,
    val ageRating: String,
    val isFavourite: Boolean,
    val tags: List<String>,
    val starRating: Int,
    val countReviews: Int,
    val timeLine: Int,
    val imageMovie: String,
    val genres: List<Genre>,
    val actors: List<Actor>
)