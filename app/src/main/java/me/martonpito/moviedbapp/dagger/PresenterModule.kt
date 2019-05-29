package me.martonpito.moviedbapp.dagger

import dagger.Module
import dagger.Provides
import me.martonpito.moviedbapp.ui.main.MainPresenter

@Module
class PresenterModule {
    @Provides
    fun provideMainPresenter(): MainPresenter = MainPresenter()
}