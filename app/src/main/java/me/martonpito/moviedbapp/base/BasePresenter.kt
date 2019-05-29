package me.martonpito.moviedbapp.base

interface BasePresenter<T> {
    fun addView(view: T)
    fun removeView()
}