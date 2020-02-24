package soy.gabimoreno.movies.data.repository

import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {

    suspend fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getPopularMovies()
    }

    suspend fun findById(id: Int) = localDataSource.findById(id)
    suspend fun update(movie: Movie) = localDataSource.update(movie)
}
