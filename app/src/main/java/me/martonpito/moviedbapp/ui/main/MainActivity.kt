package me.martonpito.moviedbapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.martonpito.MovieDBApplication
import me.martonpito.moviedbapp.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainScreen {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (MovieDBApplication).movieDBComponent.inject(this)

        presenter.getMovieList("fire")
    }
}
