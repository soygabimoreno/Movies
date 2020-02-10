package soy.gabimoreno.movies.model

import android.app.Activity
import soy.gabimoreno.movies.R

class MoviesRepository(activity: Activity) {

    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMovies(): MovieDbResult {
        return MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
            .await()
    }
}
