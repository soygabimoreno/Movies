package soy.gabimoreno.movies.data.source

import soy.gabimoreno.movies.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun getPopularMovies(): List<Movie>
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun findById(id: Int): Movie
    suspend fun update(movie: Movie)
}
