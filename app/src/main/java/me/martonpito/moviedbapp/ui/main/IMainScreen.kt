package me.martonpito.moviedbapp.ui.main

import android.content.Context
import me.martonpito.moviedbapp.network.model.Movie
import me.martonpito.moviedbapp.network.model.MovieDetails

interface IMainScreen {
    fun onMovieListReady(movieList: List<Movie>)
    fun onMoviewDetailsReady(movieDetails: MovieDetails)
    fun showProgressBar()
    fun hideProgressBar()
    fun showToast(message: String)
    fun getActivityContext(): Context
}