package soy.gabimoreno.movies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import soy.gabimoreno.movies.common.event.Event
import soy.gabimoreno.movies.common.ScopedViewModel
import soy.gabimoreno.movies.model.db.Movie
import soy.gabimoreno.movies.model.server.MoviesRepository

class MainViewModel(
    private val moviesRepository: MoviesRepository
) : ScopedViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _navigateToMovie = MutableLiveData<Event<Int>>()
    val navigateToMovie: LiveData<Event<Int>> = _navigateToMovie

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> = _requestLocationPermission

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _movies.value = moviesRepository.findPopularMovies()
            _loading.value = false
        }
    }

    fun onMovieClicked(movie: Movie) {
        val movieId = movie.id
        _navigateToMovie.value = Event(movieId)
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}
