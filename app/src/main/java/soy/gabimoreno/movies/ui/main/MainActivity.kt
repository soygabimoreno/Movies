package soy.gabimoreno.movies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.Movie
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.gone
import soy.gabimoreno.movies.ui.common.startActivity
import soy.gabimoreno.movies.ui.common.visible
import soy.gabimoreno.movies.ui.detail.DetailActivity

class MainActivity :
    AppCompatActivity(),
    MainPresenter.View {

    private val presenter: MainPresenter by lazy {
        MainPresenter(MoviesRepository(this))
    }

    private val adapter = MoviesAdapter {
        presenter.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate(this)

        rv.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        pb.visible()
    }

    override fun hideProgress() {
        pb.gone()
    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, movie)
        }
    }
}
