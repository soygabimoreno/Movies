package soy.gabimoreno.movies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.Movie
import soy.gabimoreno.movies.ui.common.loadUrl

class DetailActivity :
    AppCompatActivity(),
    DetailPresenter.View {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movie = intent.getParcelableExtra<Movie>(MOVIE)
            ?: throw (IllegalStateException("Movie not found"))
        presenter.onCreate(this, movie)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(movie: Movie) = with(movie) {
        tb.title = title
        val background = backdropPath ?: posterPath
        iv.loadUrl("https://image.tmdb.org/t/p/w780$background")
        tvSummary.text = overview
        tvInfo.setMovie(this)
    }
}
