package com.example.moviesapp.storage

import androidx.room.TypeConverter
import com.example.moviesapp.model.entities.movies.details.Genre
import com.google.gson.Gson

class ListGenresConverter {
    @TypeConverter
    fun listGenresIdsToString(genres: List<Genre>?): String = Gson().toJson(genres)

    @TypeConverter
    fun stringToListGenresIds(genresString: String): List<Genre>? {
        val gson = Gson()
        val objects = gson.fromJson(genresString, ArrayList::class.java)
        val genres = mutableListOf<Genre>()
        objects.forEach { genres.add(gson.fromJson(it.toString(), Genre::class.java)) }

        return genres
    }
}