package soy.gabimoreno.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.common.event.Event
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.usecases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var moviesObserver: Observer<List<Movie>>

    @Mock
    lateinit var requestLocationPermissionObserver: Observer<Event<Unit>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(
            getPopularMovies,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `Observing LiveData launches location permission request`() {
        vm.requestLocationPermission.observeForever(requestLocationPermissionObserver)

        verify(requestLocationPermissionObserver).onChanged(vm.requestLocationPermission.value)
    }
}
