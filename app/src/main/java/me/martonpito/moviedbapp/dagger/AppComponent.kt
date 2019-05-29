package me.martonpito.moviedbapp.dagger

import dagger.Component
import me.martonpito.moviedbapp.network.MovieDBServer
import me.martonpito.moviedbapp.ui.main.MainActivity
import me.martonpito.moviedbapp.ui.main.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    AppModule::class,
    PresenterModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
    fun inject(target: MovieDBServer)
    fun inject(target: MainPresenter)
}