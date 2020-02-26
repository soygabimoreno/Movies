package soy.gabimoreno.movies.di.main

import dagger.Subcomponent
import soy.gabimoreno.movies.ui.main.MainViewModel

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModel: MainViewModel
}
