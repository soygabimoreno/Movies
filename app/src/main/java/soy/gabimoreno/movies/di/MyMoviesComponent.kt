package soy.gabimoreno.movies.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import soy.gabimoreno.movies.di.module.AppModule
import soy.gabimoreno.movies.di.module.DataModule
import soy.gabimoreno.movies.di.module.UseCaseModule
import soy.gabimoreno.movies.di.module.ViewModelsModule
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UseCaseModule::class,
        ViewModelsModule::class
    ]
)
interface MyMoviesComponent {

    val mainViewModel: MainViewModel
    val detailViewModel: DetailViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}
