package com.example.moviesapp.ui.presentation.movies.utils

import java.util.*

fun getDate(year: Int?, month: Int?, day: Int?) : Calendar {
    val date = Calendar.getInstance()
    val todayDate = Calendar.getInstance()
    date.set(
        year ?: todayDate.get(Calendar.YEAR),
        month ?: todayDate.get(Calendar.MONTH),
        day ?: todayDate.get(Calendar.DAY_OF_MONTH)
    )

    return date
}