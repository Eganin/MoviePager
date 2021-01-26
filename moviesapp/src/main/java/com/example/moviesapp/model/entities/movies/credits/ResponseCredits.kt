package com.example.moviesapp.model.entities.movies.credits

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
