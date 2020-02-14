package soy.gabimoreno.movies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.common.loadUrl
import soy.gabimoreno.movies.model.server.MoviesRepository

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "DetailActivity:movieId"
    }

    private lateinit var vm: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        vm = getViewModel { DetailViewModel(movieId, MoviesRepository(app)) }
        vm.model.observe(this, Observer(::updateUi))

        initFab()
    }

    private fun initFab() {
        fab.setOnClickListener {
            vm.onFavoriteClicked()
        }
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        tb.title = title
        val background = backdropPath ?: posterPath
        iv.loadUrl("https://image.tmdb.org/t/p/w780$background")
        tvSummary.text = overview
        tvInfo.setMovie(this)

        val iconResId = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        fab.setImageDrawable(getDrawable(iconResId))
    }
}
