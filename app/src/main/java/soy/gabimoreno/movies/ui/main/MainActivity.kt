package soy.gabimoreno.movies.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.getViewModel
import soy.gabimoreno.movies.ui.common.setVisibleOrGone
import soy.gabimoreno.movies.ui.common.startActivity
import soy.gabimoreno.movies.ui.detail.DetailActivity
import soy.gabimoreno.movies.ui.main.MainViewModel.UiModel
import soy.gabimoreno.movies.ui.main.MainViewModel.UiModel.Loading

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel { MainViewModel(MoviesRepository(application)) }
        adapter = MoviesAdapter(viewModel::onMovieClicked)
        rv.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {
        pb.setVisibleOrGone(model == Loading)
        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> {
                startActivity<DetailActivity> {
                    putExtra(DetailActivity.MOVIE, model.movie)
                }
            }
            UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}
