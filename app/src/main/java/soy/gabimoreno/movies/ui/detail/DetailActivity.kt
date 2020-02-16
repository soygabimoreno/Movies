package soy.gabimoreno.movies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.common.loadUrl
import soy.gabimoreno.movies.databinding.ActivityDetailBinding
import soy.gabimoreno.movies.model.server.MoviesRepository

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "DetailActivity:movieId"
    }

    private lateinit var vm: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)

        val movieId = intent.getIntExtra(MOVIE_ID, -1)
        vm = getViewModel { DetailViewModel(movieId, MoviesRepository(app)) }

        binding.vm = vm
        binding.lifecycleOwner = this
    }
}
