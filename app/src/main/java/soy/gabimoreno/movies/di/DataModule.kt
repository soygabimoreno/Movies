package soy.gabimoreno.movies.di

import dagger.Module
import dagger.Provides
import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.data.repository.RegionRepository
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.LocationDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Keys
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(
        locationDataSource,
        permissionChecker
    )

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named(Keys.API_KEY) apiKey: String
    ) = MoviesRepository(
        localDataSource,
        remoteDataSource,
        regionRepository,
        apiKey
    )
}
