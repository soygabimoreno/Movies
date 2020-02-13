package soy.gabimoreno.movies.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import soy.gabimoreno.movies.App
import soy.gabimoreno.movies.PermissionRequester
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.*
import soy.gabimoreno.movies.model.db.Movie
import soy.gabimoreno.movies.model.server.MoviesRepository
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
        viewModel = getViewModel { MainViewModel(MoviesRepository(app)) }
        adapter = MoviesAdapter(viewModel::onMovieClicked)
        rv.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.navigation.observe(this, Observer(::navigate))
    }

    private fun navigate(event: Event<Movie>) {
        event.getContentIfNotHandled()?.let {
            val movie = it
            startActivity<DetailActivity> {
                val movieId = movie.id
                putExtra(DetailActivity.MOVIE_ID, movieId)
            }
        }
    }

    private fun updateUi(model: UiModel) {
        pb.setVisibleOrGone(model == Loading)
        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}
