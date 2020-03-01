package soy.gabimoreno.movies.usecases

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.testshared.mockedMovie

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var getPopularMovies: GetPopularMovies

    @Before
    fun setUp() {
        getPopularMovies = GetPopularMovies(moviesRepository)
    }

    @Test
    fun `invoke() calls movie repository`() {
        runBlocking {
            val id = 1
            val movies = listOf(mockedMovie.copy(id))
            whenever(moviesRepository.getPopularMovies()).thenReturn(movies)

            val result = getPopularMovies.invoke()

            assertEquals(movies, result)
        }
    }
}
