package com.example.moviesapp.model.entities.movies.details


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapp.storage.ListGenresConverter

import com.example.moviesapp.storage.MoviesContact
import kotlinx.serialization.*

@Entity(
    tableName = MoviesContact.Detail.TABLE_NAME
)
@TypeConverters(value = [ListGenresConverter::class])
@Serializable
data class ResponseMovieDetail (


    @PrimaryKey(autoGenerate = true)
    val idDb : Long? = null ,

    val adult: Boolean? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    val budget: Long? = null,

    val genres: List<Genre>? = null,

    val homepage: String? = null,

    val id: Long? = null,

    @SerialName("imdb_id")
    val imdbID: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    val overview: String? = null,

    val popularity: Double? = null,

    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val revenue: Long? = null,

    val runtime: Long? = null,

    val status: String? = null,

    val tagline: String? = null,

    val title: String? = null,

    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Long? = null
)