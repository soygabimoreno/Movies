package soy.gabimoreno.movies.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import soy.gabimoreno.movies.PermissionRequester
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.bindingInflate
import soy.gabimoreno.movies.common.event.EventObserver
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.data.repository.RegionRepository
import soy.gabimoreno.movies.databinding.FragmentMainBinding
import soy.gabimoreno.movies.model.AndroidPermissionChecker
import soy.gabimoreno.movies.model.PlayServicesLocationDataSource
import soy.gabimoreno.movies.model.db.RoomDataSource
import soy.gabimoreno.movies.model.server.MovieDbDataSource
import soy.gabimoreno.movies.usecases.GetPopularMovies

class MainFragment : Fragment() {

    private val vm: MainViewModel by lazy {
        getViewModel {
            val localDataSource = RoomDataSource(app.db)
            MainViewModel(
                GetPopularMovies(
                    MoviesRepository(
                        localDataSource,
                        MovieDbDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        app.getString(R.string.api_key)
                    )
                )
            )
        }
    }
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        activity?.let {
            PermissionRequester(it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
    private lateinit var navController: NavController
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        initNavigateToMovie()
        initRequestLocationPermission()

        adapter = MoviesAdapter(vm::onMovieClicked)
        binding?.apply {
            vm = this@MainFragment.vm
            lifecycleOwner = this@MainFragment
            rv.adapter = adapter
        }
    }

    private fun initNavigateToMovie() {
        vm.navigateToMovie.observe(
            this,
            EventObserver { movieId ->
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movieId)
                navController.navigate(action)
            })
    }

    private fun initRequestLocationPermission() {
        vm.requestLocationPermission.observe(
            this,
            EventObserver {
                coarsePermissionRequester?.request {
                    vm.onCoarsePermissionRequested()
                } ?: Toast.makeText(context, "There is a problem requesting permissions", Toast.LENGTH_LONG).show()
            })
    }
}
