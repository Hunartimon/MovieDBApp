package me.martonpito.moviedbapp.network.model

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("results")
    var results: List<Movie>
)