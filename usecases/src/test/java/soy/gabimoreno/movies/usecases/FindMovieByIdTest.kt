package soy.gabimoreno.movies.usecases

import com.nhaarman.mockitokotlin2.verify
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
class FindMovieByIdTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var findMovieById: FindMovieById

    @Before
    fun setUp() {
        findMovieById = FindMovieById(moviesRepository)
    }

    @Test
    fun `invoke calls movie repository`() {
        runBlocking {
            val id = 1
            val movie = mockedMovie.copy(id)
            whenever(moviesRepository.findById(id)).thenReturn(movie)

            val result = findMovieById.invoke(id)

            assertEquals(movie, result)
        }
    }
}
