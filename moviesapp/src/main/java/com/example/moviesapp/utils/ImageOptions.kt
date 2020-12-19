package com.example.moviesapp.utils

import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R

internal val imageOption = RequestOptions()
    .placeholder(R.drawable.ic_baseline_person_24)
    .fallback(R.drawable.ic_baseline_person_24)