package com.example.moviesapp.model.entities.movies.credits

import androidx.room.*
import com.example.moviesapp.storage.CastConverter
import com.example.moviesapp.storage.MoviesContact
import kotlinx.serialization.*
@Entity(
    tableName = MoviesContact.Cast.TABLE_NAME,
)
@TypeConverters(value = [CastConverter::class])
@Serializable
data class ResponseCredits (
    @ColumnInfo(name = MoviesContact.Result.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val idDb : Long? = null ,

    val id: Long,

    val cast: List<Cast>
)
