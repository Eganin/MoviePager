package com.example.moviesapp.model.repositories

import androidx.work.Constraints
import androidx.work.WorkRequest

interface RepositoryForWorker {
    val constraints : Constraints
    val request : WorkRequest



}