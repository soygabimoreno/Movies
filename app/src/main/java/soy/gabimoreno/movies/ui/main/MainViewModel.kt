package soy.gabimoreno.movies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import soy.gabimoreno.movies.model.Movie
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.Event
import soy.gabimoreno.movies.ui.common.Scope

class MainViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel(), Scope by Scope.Impl() {

    init {
        initScope()
    }

    sealed class UiModel {
        object RequestLocationPermission : UiModel()
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Movie>>()
    val navigation: LiveData<Event<Movie>> = _navigation

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(moviesRepository.findPopularMovies().results)
        }
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }

    fun onMovieClicked(movie: Movie) {
        _navigation.value = Event(movie)
    }
}