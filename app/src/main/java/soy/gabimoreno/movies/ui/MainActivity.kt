package soy.gabimoreno.movies.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.MoviesRepository
import soy.gabimoreno.movies.ui.common.CoroutineScopeActivity

class MainActivity : CoroutineScopeActivity() {

    private val moviesRepository: MoviesRepository by lazy { MoviesRepository(this) }

    private lateinit var activity: MainActivity

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this@MainActivity

        rv.adapter = adapter
        launch {
            adapter.movies = moviesRepository.findPopularMovies().results
        }
    }
}
