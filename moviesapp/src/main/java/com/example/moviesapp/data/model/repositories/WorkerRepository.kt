package com.example.moviesapp.model.repositories

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import com.example.moviesapp.storage.workmanger.WorkerMovie
import java.util.concurrent.TimeUnit


class WorkerRepository : RepositoryForWorker {

    override val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build()


    override val request = PeriodicWorkRequest
        .Builder(WorkerMovie::class.java, 16, TimeUnit.MINUTES, 15 , TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()



}