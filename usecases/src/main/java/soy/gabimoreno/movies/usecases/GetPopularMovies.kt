package soy.gabimoreno.movies.usecases

import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}
