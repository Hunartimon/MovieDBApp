package me.martonpito.moviedbapp.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.martonpito.MovieDBApplication
import me.martonpito.moviedbapp.R
import me.martonpito.moviedbapp.eventbus.EventBus
import me.martonpito.moviedbapp.network.model.Movie
import me.martonpito.moviedbapp.network.model.MovieDetails
import me.martonpito.moviedbapp.ui.main.adapter.MainAdapter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IMainScreen {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var eventBus: EventBus

    private var eventBusDisposable: CompositeDisposable? = null

    private var adapter: MainAdapter? = null

    private var isSearchBarVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (MovieDBApplication).movieDBComponent.inject(this)

        eventBusDisposable = CompositeDisposable()

        presenter.addView(this)
        presenter.getMovieList("")

        adapter = MainAdapter()
        adapter?.setHasStableIds(true)
        rv_content.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_content.adapter = adapter

        setupSearchBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBusDisposable?.clear()
    }

    override fun onBackPressed() {
        if (isSearchBarVisible) {
            hideSearchBar(true)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupSearchBar() {
        ib_close.setOnClickListener {
            search_field.text.clear()
        }
        ib_search.setOnClickListener {
            showSearchBar(true)
        }
        ib_back.setOnClickListener {
            hideSearchBar(true)
        }
        val subscription = RxTextView.textChanges(search_field)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val text = it.toString()
                eventBus.putSearchString(text)
                if (text.isNotEmpty()) {
                    ib_close.visibility = View.VISIBLE
                    search_field.hint = ""
                } else {
                    ib_close.visibility = View.INVISIBLE
                }
            }
        eventBusDisposable?.add(subscription)
    }

    private fun hideSearchBar(deleteSearchField: Boolean) {
        hideSoftKeyboard(search_field)
        search_container.animate()
            .alpha(0.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    if (deleteSearchField) {
                        search_field.text.clear()
                        isSearchBarVisible = false
                    }
                    search_container.visibility = View.GONE
                }
            })
    }

    private fun showSearchBar(showKeyBoard: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        isSearchBarVisible = true
        eventBus.putSearchString(search_field.text.toString())
        search_container.visibility = View.VISIBLE
        search_container.alpha = 0.0f
        search_container.animate()
            .alpha(1.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    search_field.requestFocus()
                    if (showKeyBoard) {
                        showSoftKeyboard()
                    }
                }
            })
    }

    private fun showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onMovieListReady(movieList: List<Movie>) {
        if (movieList.isEmpty()) {
            rv_content?.visibility = View.GONE
            tv_empty_result?.visibility = View.VISIBLE
        } else {
            rv_content?.visibility = View.VISIBLE
            tv_empty_result?.visibility = View.GONE
            adapter?.setList(movieList)
        }
    }

    override fun onMoviewDetailsReady(movieDetails: MovieDetails) {
        adapter?.onItemChange(movieDetails)
    }

    override fun showProgressBar() {
        progress_bar?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar?.visibility = View.GONE
    }
}
