package soy.gabimoreno.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.FakeLocalDataSource
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.defaultFakeMovies
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.initMockedDi
import soy.gabimoreno.movies.testshared.mockedMovie
import soy.gabimoreno.movies.usecases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var moviesObserver: Observer<List<Movie>>

    private lateinit var vm: MainViewModel

    @Before
    fun setup() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `Data is loaded from server when local data source is empty`() {
        vm.movies.observeForever(moviesObserver)

        vm.onCoarsePermissionRequested()

        verify(moviesObserver).onChanged(defaultFakeMovies)
    }

    @Test
    fun `Data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(id = 10), mockedMovie.copy(id = 11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.movies.observeForever(moviesObserver)

        vm.onCoarsePermissionRequested()

        verify(moviesObserver).onChanged(fakeLocalMovies)
    }
}
