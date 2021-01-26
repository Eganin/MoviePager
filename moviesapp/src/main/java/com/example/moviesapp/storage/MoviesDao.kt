package com.example.moviesapp.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.model.entities.movies.credits.Cast
import com.example.moviesapp.model.entities.movies.credits.ResponseCredits
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import com.example.moviesapp.model.entities.movies.popular.results.Result
import com.example.moviesapp.model.entities.movies.popular.results.ResultNowPlayong
import com.example.moviesapp.model.entities.movies.popular.results.ResultTopRated
import com.example.moviesapp.model.entities.movies.popular.results.ResultUpComing

@Dao
interface MoviesDaoPopular {
    @Query("SELECT * FROM movies")
    fun getAllMoviesPopular(): LiveData<List<Result>>

    @Insert
    suspend fun insertPopularMovies(movies: List<Result>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllPopularMovies()

    @Query("DELETE FROM movies WHERE _id == :id")
    suspend fun deletePopularMovieById(id: Long)

}

@Dao
interface MoviesDaoTopRated {
    @Query("SELECT * FROM top_rated_movies")
    fun getAllMoviesTopRated(): LiveData<List<ResultTopRated>>

    @Insert
    suspend fun insertTopRatedMovies(movies: List<ResultTopRated>)

    @Query("DELETE FROM top_rated_movies")
    suspend fun deleteAllTopRatedMovies()

    @Query("DELETE FROM top_rated_movies WHERE _id == :id")
    suspend fun deleteTopRatedMovieById(id: Long)

}

@Dao
interface MoviesDaoNowPlayong {
    @Query("SELECT * FROM now_playong_movies")
    fun getAllMoviesNowPlayong(): LiveData<List<ResultNowPlayong>>

    @Insert
    suspend fun insertNowPlayongMovies(movies: List<ResultNowPlayong>)

    @Query("DELETE FROM now_playong_movies")
    suspend fun deleteAllNowPlayongMovies()

    @Query("DELETE FROM now_playong_movies WHERE _id == :id")
    suspend fun deleteNowPlayongMovieById(id: Long)

}

@Dao
interface MoviesDaoUpComing {
    @Query("SELECT * FROM up_coming_movies")
    fun getAllMoviesUpComing(): LiveData<List<ResultUpComing>>

    @Insert
    suspend fun insertUpComingMovies(movies: List<ResultUpComing>)

    @Query("DELETE FROM up_coming_movies")
    suspend fun deleteAllUpComingMovies()

    @Query("DELETE FROM up_coming_movies WHERE _id == :id")
    suspend fun deleteUpComingMovieById(id: Long)

}

@Dao
interface  MoviesDaoDetail{
    @Query("SELECT * FROM details")
    fun getAllDetailMovies() : LiveData<List<ResponseMovieDetail>>

    @Insert
    suspend fun insertDetailMovie(movie : ResponseMovieDetail)

    @Query("DELETE FROM details")
    suspend fun deleteAllDetailMovies()

    @Query("SELECT * FROM details WHERE id == :id")
    suspend fun getDetailMovieById(id : Long) : ResponseMovieDetail

}
@Dao
interface MoviesDaoCast{
    @Query("SELECT * FROM casts")
    fun getAllCastMovies() : LiveData<List<ResponseCredits>>

    @Insert
    suspend fun insertCastMovie(cast : ResponseCredits)

    @Query("DELETE FROM casts")
    suspend fun deleteAllCast()

    @Query("SELECT * FROM casts WHERE id == :id")
    suspend fun getCastMovieById(id : Long) : ResponseCredits
}
