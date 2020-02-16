package soy.gabimoreno.movies.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import soy.gabimoreno.movies.PermissionRequester
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.event.EventObserver
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.common.startActivity
import soy.gabimoreno.movies.databinding.ActivityMainBinding
import soy.gabimoreno.movies.model.server.MoviesRepository
import soy.gabimoreno.movies.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by lazy {
        getViewModel {
            MainViewModel(MoviesRepository(app))
        }
    }
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.vm = vm
        binding.lifecycleOwner = this

        adapter = MoviesAdapter(vm::onMovieClicked)
        binding.rv.adapter = adapter

        initNavigateToMovie()
        initRequestLocationPermission()
    }

    private fun initRequestLocationPermission() {
        vm.requestLocationPermission.observe(
            this,
            EventObserver {
                coarsePermissionRequester.request {
                    vm.onCoarsePermissionRequested()
                }
            })
    }

    private fun initNavigateToMovie() {
        vm.navigateToMovie.observe(
            this,
            EventObserver { movieId ->
                startActivity<DetailActivity> {
                    putExtra(DetailActivity.MOVIE_ID, movieId)
                }
            })
    }
}
