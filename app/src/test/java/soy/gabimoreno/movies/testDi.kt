package soy.gabimoreno.movies

import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.LocationDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Keys
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.testshared.mockedMovie

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named(Keys.API_KEY)) { "123456" }
    factory<LocalDataSource> { FakeLocalDataSource() }
    factory<RemoteDataSource> { FakeRemoteDataSource() }
    factory<LocationDataSource> { FakeLocationDataSource() }
    factory<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var movies: List<Movie> = emptyList()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun getPopularMovies(): List<Movie> = movies

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun findById(id: Int): Movie = movies.first { it.id == id }

    override suspend fun update(movie: Movie) {
        movies = movies.filterNot { it.id == movie.id } + movie
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var movies: List<Movie> = defaultFakeMovies

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> = movies
}

class FakeLocationDataSource : LocationDataSource {
    override suspend fun findLastRegion(): String = "US"
}

class FakePermissionChecker : PermissionChecker {
    override suspend fun check(permission: PermissionChecker.Permission) = true
}