package soy.gabimoreno.movies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.databinding.ActivityDetailBinding
import soy.gabimoreno.movies.model.server.MoviesRepository

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "DetailActivity:movieId"
    }

    private val vm: DetailViewModel by lazy {
        getViewModel {
            val movieId = intent.getIntExtra(MOVIE_ID, -1)
            DetailViewModel(movieId, MoviesRepository(app))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        binding.vm = vm
        binding.lifecycleOwner = this
    }
}
