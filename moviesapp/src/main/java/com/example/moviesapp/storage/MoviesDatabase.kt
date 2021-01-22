package com.example.moviesapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.model.entities.movies.popular.Result

@Database(entities = [Result::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

    companion object {
        private var db: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(MoviesDatabase::class.java) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        MoviesDatabase::class.java,
                        MoviesContact.DATABASE_NAME
                    ).build()
                db = instance
                return instance
            }
        }
    }
}