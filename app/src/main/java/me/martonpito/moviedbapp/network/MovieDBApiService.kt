package me.martonpito.moviedbapp.network

import io.reactivex.Observable
import me.martonpito.moviedbapp.network.model.MovieDetails
import me.martonpito.moviedbapp.network.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

interface MovieDBApiService {

    @GET("search/movie")
    fun getMovies(@QueryMap queryParams: Map<String, String>): Observable<SearchResponse>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Int, @QueryMap queryParams: Map<String, String>): Observable<MovieDetails>

    companion object Factory {

        private fun getBaseUrl(): String {
            return "https://api.themoviedb.org/3/"
        }

        fun create(): MovieDBApiService {
            val client = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(getBaseUrl())
                .build()
            return retrofit.create(MovieDBApiService::class.java)
        }
    }
}