package com.example.moviesapp.presentation.movies.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun List<ResultUpComing>.upComingToResult(): List<Result> {
    val newResult = mutableListOf<Result>()
    for (i in this) {
        newResult.add(
            Result(
                adult = i.adult,
                backdropPath = i.backdropPath,
                genreIDS = i.genreIDS,
                idDb = i.idDb,
                id = i.id,
                originalLanguage = i.originalLanguage,
                originalTitle = i.originalTitle,
                overview = i.overview,
                popularity = i.popularity,
                posterPath = i.posterPath,
                releaseDate = i.releaseDate,
                title = i.title,
                video = i.video,
                voteAverage = i.voteAverage,
                voteCount = i.voteCount
            )
        )
    }

    return newResult
}

fun List<ResultTopRated>.topRatedToResult(): List<Result> {
    val newResult = mutableListOf<Result>()
    for (i in this) {
        newResult.add(
            Result(
                adult = i.adult,
                backdropPath = i.backdropPath,
                genreIDS = i.genreIDS,
                idDb = i.idDb,
                id = i.id,
                originalLanguage = i.originalLanguage,
                originalTitle = i.originalTitle,
                overview = i.overview,
                popularity = i.popularity,
                posterPath = i.posterPath,
                releaseDate = i.releaseDate,
                title = i.title,
                video = i.video,
                voteAverage = i.voteAverage,
                voteCount = i.voteCount
            )
        )
    }

    return newResult
}

fun List<ResultNowPlayong>.nowPlayongToResult(): List<Result> {
    val newResult = mutableListOf<Result>()
    for (i in this) {
        newResult.add(
            Result(
                adult = i.adult,
                backdropPath = i.backdropPath,
                genreIDS = i.genreIDS,
                idDb = i.idDb,
                id = i.id,
                originalLanguage = i.originalLanguage,
                originalTitle = i.originalTitle,
                overview = i.overview,
                popularity = i.popularity,
                posterPath = i.posterPath,
                releaseDate = i.releaseDate,
                title = i.title,
                video = i.video,
                voteAverage = i.voteAverage,
                voteCount = i.voteCount
            )
        )
    }

    return newResult
}
