package com.example.moviesapp.application

import android.app.Application
import com.example.moviesapp.di.AppComponent

class MovieApp : Application() {

    val myComponent: AppComponent by lazy { AppComponent(this) }
}