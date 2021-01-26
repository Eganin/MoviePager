package com.example.moviesapp.storage

import android.util.Log
import androidx.room.TypeConverter
import com.example.moviesapp.model.entities.movies.details.Genre
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ListGenresConverter {
    private val gson = GsonBuilder()
        .serializeNulls()
        .serializeSpecialFloatingPointValues()
        .setLenient()
        .create()

    @TypeConverter
    fun listGenresToString(genres: List<Genre>?): String = gson.toJson(genres)

    @TypeConverter
    fun stringToListGenres(genresString: String): List<Genre>? {
        val genres = mutableListOf<Genre>()

        val objects = gson.fromJson(genresString.trim(), ArrayList::class.java)
        objects.forEach { genres.add(gson.fromJson(it.toString().trim(), Genre::class.java)) }
        objects.forEach { Log.d("AAA",it.toString()) }

        return genres
    }
}