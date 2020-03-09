package soy.gabimoreno.movies

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.data.repository.RegionRepository
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.data.source.LocationDataSource
import soy.gabimoreno.movies.data.source.RemoteDataSource
import soy.gabimoreno.movies.domain.Keys
import soy.gabimoreno.movies.model.AndroidPermissionChecker
import soy.gabimoreno.movies.model.PlayServicesLocationDataSource
import soy.gabimoreno.movies.model.db.MovieDatabase
import soy.gabimoreno.movies.model.db.RoomDataSource
import soy.gabimoreno.movies.model.server.MovieDbDataSource
import soy.gabimoreno.movies.ui.detail.DetailFragment
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainFragment
import soy.gabimoreno.movies.ui.main.MainViewModel
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.GetPopularMovies
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule))
    }
}

private val appModule = module {
    single(named(Keys.API_KEY)) {
        androidApplication().getString(R.string.api_key)
    }
    single {
        MovieDatabase.build(get())
    }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { MovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named(Keys.API_KEY))) }
}

private val scopesModule = module {
    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}
