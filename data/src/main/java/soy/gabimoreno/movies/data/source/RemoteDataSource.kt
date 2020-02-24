package soy.gabimoreno.movies.data.source

import soy.gabimoreno.movies.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}
