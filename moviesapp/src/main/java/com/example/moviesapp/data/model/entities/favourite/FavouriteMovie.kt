package com.example.moviesapp.model.entities.favourite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesapp.storage.MoviesContact
import kotlinx.serialization.Serializable

@Entity(
    tableName = MoviesContact.Favourite.TABLE_NAME,
)
@Serializable
data class FavouriteMovie(

    @ColumnInfo(name= MoviesContact.Favourite.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val idDb : Long?=null,

    val id : Long?=null
)