package me.martonpito.moviedbapp.ui.main

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.martonpito.MovieDBApplication
import me.martonpito.moviedbapp.base.BasePresenter
import me.martonpito.moviedbapp.network.MovieDBServer
import javax.inject.Inject

class MainPresenter: BasePresenter<IMainScreen> {

    @Inject
    lateinit var server: MovieDBServer

    private var mainScreen: IMainScreen? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        MovieDBApplication.movieDBComponent.inject(this)
    }

    fun getMovieList(searchText: String) {
        val params = mapOf(
            "query" to searchText,
            "api_key" to "43a7ea280d085bd0376e108680615c7f",
            "language" to "en-US",
            "include_adult" to "false"
        )
        val call = server.apiService.getMovies(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(TAG, response.toString())
            }, { error ->
                error.printStackTrace()
            })
        compositeDisposable?.add(call)
    }

    override fun addView(view: IMainScreen) {
        mainScreen = view
        compositeDisposable = CompositeDisposable()
    }

    override fun removeView() {
        mainScreen = null
        compositeDisposable?.dispose()
    }

    companion object {
        val TAG = MainPresenter.javaClass.simpleName
    }
}