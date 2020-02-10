package soy.gabimoreno.movies.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.getParcelableExtra<Movie>(MOVIE)?.run {
            tb.title = title
            val background = backdropPath ?: posterPath
            iv.loadUrl("https://image.tmdb.org/t/p/w780$background")
            tvSummary.text = overview
            tvInfo.setMovie(this)
        }
    }
}
