package soy.gabimoreno.movies.di.main

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.ui.main.MainViewModel
import soy.gabimoreno.movies.usecases.GetPopularMovies

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(
        getPopularMovies: GetPopularMovies,
        uiDispatcher: CoroutineDispatcher
    ) =
        MainViewModel(getPopularMovies, uiDispatcher)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}
