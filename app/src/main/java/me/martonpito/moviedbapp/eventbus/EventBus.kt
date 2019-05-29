package me.martonpito.moviedbapp.eventbus

import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class EventBus {

    private val searchProcessor = BehaviorProcessor.create<String>()

    val searchResult: Flowable<String>
        get() = searchProcessor

    fun putSearchString(string: String) {
        searchProcessor.onNext(string)
    }

}