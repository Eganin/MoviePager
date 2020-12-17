package com.example.moviesapp.domain

import com.example.moviesapp.data.models.Movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                title = "Avengers end game",
                ageRating = "13+",
                isFavourite = true,
                tags = listOf("Action", "Adventure"),
                starRating = 5,
                countReviews = 125,
                timeLine = 125,
                imageMovie = "https://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg"
            ),
            Movie(
                title = "Black window",
                ageRating = "13+",
                isFavourite = true,
                tags = listOf("Action", "Adventure", "Sci-Fi"),
                starRating = 4,
                countReviews = 150,
                timeLine = 102,
                imageMovie = "https://maincream.com/upload/k003i2qyt6c31-jsmofy.jpg"
            ),
            Movie(
                title = "Tenet",
                ageRating = "16+",
                isFavourite = false,
                tags = listOf("Action"),
                starRating = 5,
                countReviews = 180,
                timeLine = 160,
                imageMovie = "https://st.kp.yandex.net/im/poster/3/4/5/kinopoisk.ru-Tenet-3450246--o--.jpg"
            ),
            Movie(
                title = "Wonder woman",
                ageRating = "13+",
                isFavourite = false,
                tags = listOf("Action", "Adventure", "Fantasy"),
                starRating = 5,
                countReviews = 70,
                timeLine = 120,
                imageMovie = "https://www.film.ru/sites/default/files/movies/posters/7277537-920152.jpg"
            ),
            Movie(
                title = "Wonder woman",
                ageRating = "13+",
                isFavourite = false,
                tags = listOf("Action", "Adventure", "Fantasy"),
                starRating = 5,
                countReviews = 70,
                timeLine = 120,
                imageMovie = "https://www.film.ru/sites/default/files/movies/posters/7277537-920152.jpg"
            )
        )

    }
}