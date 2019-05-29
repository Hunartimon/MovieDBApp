package me.martonpito

import android.app.Application
import me.martonpito.moviedbapp.dagger.AppComponent
import me.martonpito.moviedbapp.dagger.AppModule
import me.martonpito.moviedbapp.dagger.DaggerAppComponent
import me.martonpito.moviedbapp.dagger.NetworkModule
import com.squareup.picasso.Picasso
import com.squareup.picasso.OkHttp3Downloader



class MovieDBApplication: Application() {

    companion object{
        lateinit var movieDBComponent: AppComponent
    }

    init {
        movieDBComponent = initDagger(this)
    }

    override fun onCreate() {
        super.onCreate()
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this, Integer.MAX_VALUE.toLong()))
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }

    private fun initDagger(app: MovieDBApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .networkModule(NetworkModule())
            .build()
}