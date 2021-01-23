package com.example.moviesapp.model.entities.movies.popular.results

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapp.storage.GenresConventer
import com.example.moviesapp.storage.MoviesContact
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = MoviesContact.Result.TABLE_NAME_NOW_PLAYONG,
)
@TypeConverters(value = [GenresConventer::class])
@Serializable
data class ResultNowPlayong  (
    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_ADULT)
    val adult: Boolean? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_BACKDROP_PATH)
    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_GENRES_IDS)
    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val idDb : Long? = null ,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_MOVIE)
    val id: Long,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_ORIGINAL_LANGUAGE)
    @SerialName("original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_ORIGINAL_TITLE)
    @SerialName("original_title")
    val originalTitle: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_OVERVIEW)
    val overview: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_POPULARITY)
    val popularity: Double? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_POSTER_PATH)
    @SerialName("poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_RELEASE_DATE)
    @SerialName("release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_TITLE)
    val title: String? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_VIDEO)
    val video: Boolean? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_VOTE_AVERAGE)
    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_VOTE_COUNT)
    @SerialName("vote_count")
    val voteCount: Long? = null
)