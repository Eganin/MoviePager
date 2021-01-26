package com.example.moviesapp.storage

import androidx.room.TypeConverter
import com.google.gson.Gson

class GenresConverter {
    @TypeConverter
    fun listGenresIdsToString(genresIds: List<Long>?): String = Gson().toJson(genresIds)

    @TypeConverter
    fun stringToListGenresIds(genresIdsString: String): List<Long>? {
        val gson = Gson()
        val objects = gson.fromJson(genresIdsString, ArrayList::class.java)
        val genresIds = mutableListOf<Long>()
        objects.forEach { genresIds.add(gson.fromJson(it.toString(), Long::class.java)) }

        return genresIds
    }
}