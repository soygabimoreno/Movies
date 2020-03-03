package soy.gabimoreno.movies.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.testshared.mockedMovie

@RunWith(MockitoJUnitRunner::class)
class ToggleMovieFavoriteTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Before
    fun setUp() {
        toggleMovieFavorite = ToggleMovieFavorite(moviesRepository)
    }

    @Test
    fun `invoke() calls movie repository`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(id)

            val result = toggleMovieFavorite.invoke(movie)

            verify(moviesRepository).update(result)
        }
    }

    @Test
    fun `favorite movie becomes non-favorite`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(favorite = true)

            val result = toggleMovieFavorite.invoke(movie)

            assertFalse(result.favorite)
        }
    }

    @Test
    fun `non-favorite movie becomes favorite`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(favorite = false)

            val result = toggleMovieFavorite.invoke(movie)

            assertTrue(result.favorite)
        }
    }
}
