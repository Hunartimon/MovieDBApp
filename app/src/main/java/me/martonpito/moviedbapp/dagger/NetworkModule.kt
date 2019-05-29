package me.martonpito.moviedbapp.dagger

import dagger.Module
import dagger.Provides
import me.martonpito.moviedbapp.network.MovieDBServer
import javax.inject.Singleton

@Module
class NetworkModule() {
    @Provides
    @Singleton
    internal fun providerMovieDBServer(): MovieDBServer {
        return MovieDBServer()
    }
}