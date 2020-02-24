package soy.gabimoreno.movies.usecases

import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.domain.Movie

class ToggleMovieFavorite(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie) = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}
