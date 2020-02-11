package soy.gabimoreno.movies.ui.main

import kotlinx.coroutines.launch
import soy.gabimoreno.movies.model.Movie
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.Scope

class MainPresenter(
    private val moviesRepository: MoviesRepository
) : Scope by Scope.Impl() {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(movies: List<Movie>)
        fun navigateTo(movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        initScope()
        this.view = view

        launch {
            view.showProgress()
            view.updateData(moviesRepository.findPopularMovies().results)
            view.hideProgress()
        }
    }

    fun onDestroy() {
        cancelScope()
        view = null
    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }
}