package soy.gabimoreno.movies.data

import soy.gabimoreno.movies.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {

    fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getPopularMovies()
    }
}

interface LocalDataSource {
    fun isEmpty(): Boolean
    fun getPopularMovies(): List<Movie>
    fun saveMovies(movies: List<Movie>)
}

interface RemoteDataSource {
    fun getPopularMovies(apiKey: String, region: String): List<Movie>
}
