package soy.gabimoreno.movies.ui.main

import org.junit.Before
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.usecases.GetPopularMovies

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    private lateinit var vm: MainViewModel

    @Before
    fun setup() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetPopularMovies(get()) }
        }

//        initMockedDi(vmModule)
        vm = get()
    }
}
