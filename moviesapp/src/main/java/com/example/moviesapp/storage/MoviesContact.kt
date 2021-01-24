package com.example.moviesapp.storage

import android.provider.BaseColumns

object MoviesContact {
    const val DATABASE_NAME = "movies.db"

    object Result{
        const val TABLE_NAME = "movies"
        const val TABLE_NAME_TOP_RATED = "top_rated_movies"
        const val TABLE_NAME_NOW_PLAYONG = "now_playong_movies"
        const val TABLE_NAME_UP_COMING = "up_coming_movies"
        const val TABLE_NAME_FAVOURITE = "favourite"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_MOVIE = "id_movie"
        const val COLUMN_NAME_ADULT = "adult"
        const val COLUMN_NAME_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_NAME_GENRES_IDS = "genres_ids"
        const val COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language"
        const val COLUMN_NAME_ORIGINAL_TITLE = "original_title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POPULARITY = "popularity"
        const val COLUMN_NAME_POSTER_PATH = "poster_path"
        const val COLUMN_NAME_RELEASE_DATE = "release_date"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_VIDEO = "video"
        const val COLUMN_NAME_VOTE_AVERAGE = "vote_average"
        const val COLUMN_NAME_VOTE_COUNT = "vote_count"
    }
}