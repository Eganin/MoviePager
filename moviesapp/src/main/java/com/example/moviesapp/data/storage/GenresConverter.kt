package com.example.moviesapp.storage

import android.util.Log
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
    fun stringToListGenresIds(genresIdsString: String): List<Long?>? {
        val genresIds = mutableListOf<Long?>()
        val objects = gson.fromJson(genresIdsString.trim(), ArrayList::class.java)
        objects.forEach {
            try {
                genresIds.add(gson.fromJson(it.toString().trim(), Long::class.java))
            } catch (e: Exception) {
                genresIds.add(null)
            }
        }
        objects.forEach { Log.d("AAA", it.toString()) }


        return genresIds
    }
}