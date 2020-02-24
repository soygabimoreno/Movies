package soy.gabimoreno.movies.usecases

import soy.gabimoreno.movies.data.repository.MoviesRepository

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke() = moviesRepository.getPopularMovies()
}
