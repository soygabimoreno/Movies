package soy.gabimoreno.movies.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import soy.gabimoreno.movies.di.detail.DetailFragmentComponent
import soy.gabimoreno.movies.di.detail.DetailFragmentModule
import soy.gabimoreno.movies.di.main.MainFragmentComponent
import soy.gabimoreno.movies.di.main.MainFragmentModule
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class
    ]
)
interface MoviesComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MoviesComponent
    }
}
