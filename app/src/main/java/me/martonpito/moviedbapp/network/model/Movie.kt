package me.martonpito.moviedbapp.network.model

import com.google.gson.annotations.SerializedName

class Movie(@SerializedName("id")
            var id: Int,
            @SerializedName("original_title")
            var originalTitle: String,
            @SerializedName("overview")
            var overView: String,
            @SerializedName("release_date")
            var releaseDate: String,
            @SerializedName("vote_count")
            var voteCount: Int,
            @SerializedName("poster_path")
            var posterPath: String,
            var budget: Int? = null
)