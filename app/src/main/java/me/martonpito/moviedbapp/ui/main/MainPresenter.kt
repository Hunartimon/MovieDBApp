package me.martonpito.moviedbapp.ui.main

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.martonpito.MovieDBApplication
import me.martonpito.moviedbapp.base.BasePresenter
import me.martonpito.moviedbapp.eventbus.EventBus
import me.martonpito.moviedbapp.network.MovieDBServer
import me.martonpito.moviedbapp.network.model.Movie
import me.martonpito.moviedbapp.network.model.MovieDetails
import javax.inject.Inject

class MainPresenter: BasePresenter<IMainScreen> {

    @Inject
    lateinit var server: MovieDBServer

    @Inject
    lateinit var eventBus: EventBus

    private var mainScreen: IMainScreen? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        MovieDBApplication.movieDBComponent.inject(this)
        compositeDisposable = CompositeDisposable()
        val searchDisposable = eventBus.searchResult
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchText ->
                getMovieList(searchText)
            }, { error ->
                error.printStackTrace()
            })
        compositeDisposable?.add(searchDisposable)
    }

    fun getMovieList(searchText: String?) {
        mainScreen?.showProgressBar()
        val params = mutableMapOf(
            "api_key" to "43a7ea280d085bd0376e108680615c7f",
            "language" to "en-US",
            "include_adult" to "false",
            "query" to (if(searchText.isNullOrBlank()) "a" else  searchText)
        )
        val call = server.apiService.getMovies(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Log.d(TAG, response.toString())
                mainScreen?.hideProgressBar()
                mainScreen?.onMovieListReady(response.results)
                getBulkMovieDetails(response.results)
            }, { error ->
                mainScreen?.hideProgressBar()
                error.printStackTrace()
            })
        compositeDisposable?.add(call)
    }

    private fun getBulkMovieDetails(movieList: List<Movie>) {
        val params = mapOf(
            "api_key" to "43a7ea280d085bd0376e108680615c7f",
            "language" to "en-US"
        )
        val calls = arrayListOf<Observable<MovieDetails>>()
        for (movie in movieList) {
            val call = server.apiService.getMovie(movie.id, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
            calls.add(call)
        }
        val disposableCalls = Observable.fromIterable(calls)
            .flatMap { it }
            .subscribe({ response ->
                Log.d(TAG, response.toString())
                mainScreen?.onMoviewDetailsReady(response)
            }, { error ->
                error.printStackTrace()
            })
        compositeDisposable?.add(disposableCalls)
    }

    override fun addView(view: IMainScreen) {
        mainScreen = view
    }

    override fun removeView() {
        mainScreen = null
        compositeDisposable?.dispose()
    }

    companion object {
        val TAG = MainPresenter.javaClass.simpleName
    }
}