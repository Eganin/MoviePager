package com.example.moviesapp.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.model.entities.movies.popular.Result

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Result>

    @Insert
    suspend fun insertMovies(movies: List<Result>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movies WHERE _id == :id")
    suspend fun deleteMovieById(id: Long)
}