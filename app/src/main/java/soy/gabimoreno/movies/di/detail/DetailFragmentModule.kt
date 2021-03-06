package soy.gabimoreno.movies.di.detail

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainViewModel
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.GetPopularMovies
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

@Module
class DetailFragmentModule(private val movieId: Int) {

    @Provides
    fun detailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite,
        uiDispatcher: CoroutineDispatcher
    ) = DetailViewModel(
        movieId,
        findMovieById,
        toggleMovieFavorite,
        uiDispatcher
    )

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) =
        FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}
