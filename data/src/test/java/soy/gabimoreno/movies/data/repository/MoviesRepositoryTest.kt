package soy.gabimoreno.movies.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Movie

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MoviesRepository

    private val apiKey = "1a2b3c4d"

    private val mockedMovie = Movie(
        0,
        "Title",
        "Overview",
        "01/01/2025",
        "",
        "",
        "EN",
        "Title",
        5.0,
        5.1,
        false
    )

    @Before
    @Throws(Exception::class)
    fun setUp() {
        moviesRepository = MoviesRepository(
            localDataSource,
            remoteDataSource,
            regionRepository,
            apiKey
        )
    }

    @Test
    fun `getPopularMovies gets from local data source first`() {
        runBlocking {
            val localMovies = listOf(mockedMovie.copy(id = 1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.getPopularMovies()

            assertEquals(localMovies, result)
        }
    }

    @Test
    fun `getPopularMovies saves remote data to local`() {
        runBlocking {
            val remoteMovies = listOf(mockedMovie.copy(id = 2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularMovies(any(), any())).thenReturn(remoteMovies)
            whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)

            moviesRepository.getPopularMovies()

            verify(localDataSource).saveMovies(remoteMovies)
        }
    }

    @Test
    fun `findById calls local data source`() {
        runBlocking {
            val id = 5
            val movie = mockedMovie.copy(id)
            whenever(localDataSource.findById(id)).thenReturn(movie)

            val result = moviesRepository.findById(id)

            assertEquals(movie, result)
        }
    }

    @Test
    fun `update updates local data source`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            moviesRepository.update(movie)

            verify(localDataSource).update(movie)
        }
    }
}
