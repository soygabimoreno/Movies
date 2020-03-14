package soy.gabimoreno.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.common.event.Event
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.testshared.mockedMovie
import soy.gabimoreno.movies.usecases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    // Foo  dddd

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var moviesObserver: Observer<List<Movie>>

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

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

    @Test
    fun `After requesting the permission, loading is shown`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.loading.observeForever(loadingObserver)

            vm.onCoarsePermissionRequested()

            verify(loadingObserver).onChanged(vm.loading.value)
        }
    }

    @Test
    fun `After requesting the permission, getPopularMovies() is called`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.movies.observeForever(moviesObserver)

            vm.onCoarsePermissionRequested()

            verify(moviesObserver).onChanged(vm.movies.value)
        }
    }
}
