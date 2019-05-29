package me.martonpito.moviedbapp.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import me.martonpito.moviedbapp.eventbus.EventBus
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideEventBus(): EventBus = EventBus()
}