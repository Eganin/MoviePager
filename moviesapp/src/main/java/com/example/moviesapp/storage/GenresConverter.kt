package com.example.moviesapp.storage

import androidx.room.TypeConverter
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GenresConverter {
    private val gson = GsonBuilder()
        .serializeNulls()
        .serializeSpecialFloatingPointValues()
        .setLenient()
        .create()
    @TypeConverter
    fun listGenresIdsToString(genresIds: List<Long>?): String = gson.toJson(genresIds)

    @TypeConverter
    fun stringToListGenresIds(genresIdsString: String): List<Long>? {

        val objects = gson.fromJson(genresIdsString.trim(), ArrayList::class.java)
        val genresIds = mutableListOf<Long>()
        objects.forEach { genresIds.add(gson.fromJson(it.toString().trim(), Long::class.java)) }

        return genresIds
    }
}