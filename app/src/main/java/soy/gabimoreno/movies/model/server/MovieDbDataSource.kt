package soy.gabimoreno.movies.model.server

import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.model.toMovie

class MovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> {
        return MovieDb.service
            .listPopularMoviesAsync(apiKey, region).await()
            .results
            .map {
                it.toMovie()
            }
    }
}
