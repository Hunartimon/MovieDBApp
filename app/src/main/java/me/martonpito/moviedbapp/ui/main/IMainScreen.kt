package me.martonpito.moviedbapp.ui.main

import me.martonpito.moviedbapp.network.model.Movie

interface IMainScreen {
    fun onMoviewListReady(movieList: List<Movie>)
}