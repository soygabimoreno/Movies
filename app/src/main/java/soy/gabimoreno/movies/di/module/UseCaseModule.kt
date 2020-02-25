package soy.gabimoreno.movies.di.module

import dagger.Module
import dagger.Provides
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.GetPopularMovies
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

@Module
class UseCaseModule {

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) = GetPopularMovies(moviesRepository)

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) = ToggleMovieFavorite(moviesRepository)
}
