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

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> = _overview

    private fun findMovie() = launch {
        _model.value = UiModel(moviesRepository.findById(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            val updatedMovie = it.copy(favorite = !it.favorite)
            _model.value = UiModel(updatedMovie)
            moviesRepository.update(updatedMovie)
        }
    }
}
