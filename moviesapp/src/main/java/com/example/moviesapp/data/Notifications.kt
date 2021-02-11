package com.example.moviesapp.data

import android.app.PendingIntent
import androidx.core.app.NotificationManagerCompat
import com.example.moviesapp.model.entities.movies.popular.results.Result
import android.content.Context
import android.content.Intent
import androidx.annotation.WorkerThread
import androidx.core.app.*
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import com.example.moviesapp.R
import com.example.moviesapp.screens.movies.MainActivity


interface Notifications {
    fun initialize()
    fun showNotification(movie: Result)
}

class MovieNotifications(private val context: Context) : Notifications {

    private val notificationManager = NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManager.getNotificationChannel(CHANNEL_MOVIES) == null) {
            val channelMovies = NotificationChannelCompat.Builder(CHANNEL_MOVIES, IMPORTANCE_HIGH)
                .setName(context.getString(R.string.mailing_movies))
                .setDescription(context.getString(R.string.all_new_movies))
                .build()
            notificationManager.createNotificationChannel(channelMovies)
        }
    }

    @WorkerThread
    override fun showNotification(movie: Result) {
        val uri = "https://moviesapp.example.com/movie/${movie.id}".toUri()

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_MOVIES)
            .setContentTitle(movie.title)
            .setContentText(movie.originalTitle)
            .setSmallIcon(R.drawable.ic_baseline_movie_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT_MOVIES,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(uri),
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )
            .setWhen(System.currentTimeMillis())

        notificationManager.notify(MOVIE_TAG , movie.id.toInt() ,notificationBuilder.build())
    }

    companion object {
        private const val CHANNEL_MOVIES = "channel_movies"
        private const val REQUEST_CONTENT_MOVIES = 80
        private const val MOVIE_TAG = "movies"
    }
}