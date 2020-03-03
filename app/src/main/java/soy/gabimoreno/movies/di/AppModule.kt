package soy.gabimoreno.movies.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.LocationDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Keys
import soy.gabimoreno.movies.model.AndroidPermissionChecker
import soy.gabimoreno.movies.model.PlayServicesLocationDataSource
import soy.gabimoreno.movies.model.db.MovieDatabase
import soy.gabimoreno.movies.model.db.RoomDataSource
import soy.gabimoreno.movies.model.server.MovieDbDataSource
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named(Keys.API_KEY)
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun dbProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java, "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = MovieDbDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource = PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker = AndroidPermissionChecker(app)

    @Provides
    fun uiDispatcherProvider(): CoroutineDispatcher = Dispatchers.Main
}
