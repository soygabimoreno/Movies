package soy.gabimoreno.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import soy.gabimoreno.movies.common.ScopedViewModel
import soy.gabimoreno.movies.model.db.Movie
import soy.gabimoreno.movies.model.server.MoviesRepository

class DetailViewModel(
    private val movieId: Int,
    private val moviesRepository: MoviesRepository
) : ScopedViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> = _overview

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> = _favorite

    init {
        launch {
            _movie.value = moviesRepository.findById(movieId)
            updateUi()
        }
    }

    fun onFavoriteClicked() {
        launch {
            _movie.value?.let {
                val updatedMovie = it.copy(favorite = !it.favorite)
                _movie.value = updatedMovie
                updateUi()
                moviesRepository.update(updatedMovie)
            }
        }
    }

    private fun updateUi() {

        _movie.value?.run {
            _title.value = title
            _overview.value = overview
            _url.value = "https://image.tmdb.org/t/p/w780$backdropPath"
            _favorite.value = favorite
        }
    }
}
