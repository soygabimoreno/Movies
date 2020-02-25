package soy.gabimoreno.movies.di.module

import dagger.Module
import dagger.Provides
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainViewModel
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.GetPopularMovies
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

@Module
class ViewModelsModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MainViewModel(getPopularMovies)

    @Provides
    fun detailViewModelProvider(
        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite
    ) = DetailViewModel(
        -1, // TODO: Pass the id
        findMovieById,
        toggleMovieFavorite
    )
}
