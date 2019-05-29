package me.martonpito

import android.app.Application
import me.martonpito.moviedbapp.dagger.AppComponent
import me.martonpito.moviedbapp.dagger.AppModule
import me.martonpito.moviedbapp.dagger.DaggerAppComponent
import me.martonpito.moviedbapp.dagger.NetworkModule

class MovieDBApplication: Application() {

    companion object{
        lateinit var movieDBComponent: AppComponent
    }

    init {
        movieDBComponent = initDagger(this)
    }

    private fun initDagger(app: MovieDBApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .networkModule(NetworkModule())
            .build()
}