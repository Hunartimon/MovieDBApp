package me.martonpito.moviedbapp.network

import me.martonpito.MovieDBApplication

class MovieDBServer {

    val apiService = MovieDBApiService.create()

    init {
        MovieDBApplication.movieDBComponent.inject(this)
    }
}