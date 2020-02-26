package soy.gabimoreno.movies.di.main

import dagger.Module
import dagger.Provides
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.ui.main.MainViewModel
import soy.gabimoreno.movies.usecases.GetPopularMovies

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) =
        MainViewModel(getPopularMovies)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}
