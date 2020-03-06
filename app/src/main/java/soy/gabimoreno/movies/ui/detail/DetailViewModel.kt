package soy.gabimoreno.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import soy.gabimoreno.movies.common.ScopedViewModel
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

class DetailViewModel(
    private val movieId: Int,
    private val findMovieById: FindMovieById,
    private val toggleMovieFavorite: ToggleMovieFavorite,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

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
            _movie.value = findMovieById.invoke(movieId)
            updateUi()
        }
    }

    fun onFavoriteClicked() {
        launch {
            _movie.value?.let {
                _movie.value = toggleMovieFavorite.invoke(it)
                updateUi()
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
