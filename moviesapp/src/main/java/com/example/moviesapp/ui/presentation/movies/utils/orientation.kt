@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.example.moviesapp.presentation.movies.utils

import android.content.Context
import android.content.res.Configuration


fun getOrientation(context: Context): String {
    return if (context.resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT) "PORTRAIT" else "LANDSPACE"
}