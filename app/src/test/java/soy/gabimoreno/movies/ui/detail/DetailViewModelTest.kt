package soy.gabimoreno.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.testshared.mockedMovie
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val movieId = 1

    @Mock
    lateinit var findMovieById: FindMovieById

    @Mock
    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var movieObserver: Observer<Movie>

    @Mock
    lateinit var favoriteObserver: Observer<Boolean>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(
            movieId,
            findMovieById,
            toggleMovieFavorite,
            Dispatchers.Unconfined
        )
    }

    @Ignore
    @Test
    fun `Observing LiveData finds the movie`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(id = id)

            whenever(findMovieById.invoke(id)).thenReturn(movie)
            vm.movie.observeForever(movieObserver)

            verify(movieObserver).onChanged(movie)
        }
    }

    @Ignore
    @Test
    fun `When favorite clicked, the toggleMovieFavorite use case is invoked`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(id = id)

            whenever(findMovieById.invoke(id)).thenReturn(movie)
            whenever(toggleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))
            vm.movie.observeForever(movieObserver)
            vm.favorite.observeForever(favoriteObserver)

            vm.onFavoriteClicked()

            verify(toggleMovieFavorite).invoke(movie)
        }
    }
}
