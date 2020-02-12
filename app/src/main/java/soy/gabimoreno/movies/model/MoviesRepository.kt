package soy.gabimoreno.movies.model

import android.app.Activity
import android.app.Application
import soy.gabimoreno.movies.R

class MoviesRepository(application: Application) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies(): MovieDbResult {
        return MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            )
            .await()
    }
}
