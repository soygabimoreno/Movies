package soy.gabimoreno.movies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.server.MoviesRepository
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.common.loadUrl

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "DetailActivity:movieId"
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        viewModel = getViewModel { DetailViewModel(movieId, MoviesRepository(app)) }
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        tb.title = title
        val background = backdropPath ?: posterPath
        iv.loadUrl("https://image.tmdb.org/t/p/w780$background")
        tvSummary.text = overview
        tvInfo.setMovie(this)
    }
}
