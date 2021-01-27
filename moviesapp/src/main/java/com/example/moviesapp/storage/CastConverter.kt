package com.example.moviesapp.storage

import android.util.Log
import androidx.room.TypeConverter
import com.example.moviesapp.model.entities.movies.credits.Cast
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CastConverter {
    private val gson = GsonBuilder()
        .serializeNulls()
        .serializeSpecialFloatingPointValues()
        .setLenient()
        .create()
    @TypeConverter
    fun listCastToString(castes: List<Cast>?): String? = gson.toJson(castes)

    @TypeConverter
    fun stringToListCast(casteString: String): List<Cast>? {

        val objects = gson.fromJson(casteString.trim(), ArrayList::class.java)
        val castes = mutableListOf<Cast>()
        objects.forEach { Log.d("AAA",it.toString()) }
        //objects.forEach { castes.add(gson.fromJson(it.toString().trim(), Cast::class.java)) }
        objects.forEach { castes.add(parseJson(jsonString=it.toString().trim()))}

        return castes

    }
    private fun parseJson(jsonString : String): Cast{
        val name = jsonString.split(",")[0].substring(6)
        return Cast(name=name)
    }
}