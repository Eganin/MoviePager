package com.example.moviesapp.presentation.movies.utils

import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R

internal val imageOptionActor = RequestOptions()
    .placeholder(R.drawable.ic_baseline_person_24)
    .fallback(R.drawable.ic_baseline_person_24)

internal val imageOptionMovie = RequestOptions()
    .placeholder(R.drawable.ic_baseline_movie_creation_24)
    .fallback(R.drawable.ic_baseline_movie_creation_24)