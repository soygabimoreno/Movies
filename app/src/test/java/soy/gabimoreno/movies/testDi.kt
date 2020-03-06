package soy.gabimoreno.movies

import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.repository.RegionRepository
import soy.gabimoreno.movies.data.source.LocationDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.testshared.mockedMovie

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> {
        return listOf(
            mockedMovie.copy(1),
            mockedMovie.copy(2),
            mockedMovie.copy(3),
            mockedMovie.copy(4)
        )
    }
}

class FakeLocationDataSource : LocationDataSource {
    override suspend fun findLastRegion(): String = "US"
}

class FakePermissionChecker : PermissionChecker {
    override suspend fun check(permission: PermissionChecker.Permission) = true
}
