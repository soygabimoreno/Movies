package soy.gabimoreno.movies.di.detail

import dagger.Subcomponent
import soy.gabimoreno.movies.ui.detail.DetailViewModel
import soy.gabimoreno.movies.ui.main.MainViewModel

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModel: DetailViewModel
}
