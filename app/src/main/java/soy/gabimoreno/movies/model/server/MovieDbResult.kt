package soy.gabimoreno.movies.model.server

import com.google.gson.annotations.SerializedName

data class MovieDbResult(
    val page: Int,
    val results: List<ServerMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
