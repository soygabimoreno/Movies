package soy.gabimoreno.movies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.Movie
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.gone
import soy.gabimoreno.movies.ui.common.setVisibleOrGone
import soy.gabimoreno.movies.ui.common.startActivity
import soy.gabimoreno.movies.ui.common.visible
import soy.gabimoreno.movies.ui.detail.DetailActivity
import soy.gabimoreno.movies.ui.main.MainViewModel.*
import soy.gabimoreno.movies.ui.main.MainViewModel.UiModel.Loading

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(MoviesRepository(this))
        )[MainViewModel::class.java]

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        rv.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {
        pb.setVisibleOrGone(model == Loading)
        when(model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> {
                startActivity<DetailActivity> {
                    putExtra(DetailActivity.MOVIE, model.movie)
                }
            }
        }
    }
}
