package soy.gabimoreno.movies.usecases

import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}
