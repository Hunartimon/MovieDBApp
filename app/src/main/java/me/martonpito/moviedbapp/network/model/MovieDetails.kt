package me.martonpito.moviedbapp.network.model

import com.google.gson.annotations.SerializedName

class MovieDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("budget")
    val budget: Int
)