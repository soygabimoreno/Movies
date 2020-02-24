package soy.gabimoreno.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.app
import soy.gabimoreno.movies.common.bindingInflate
import soy.gabimoreno.movies.common.getViewModel
import soy.gabimoreno.movies.data.repository.MoviesRepository
import soy.gabimoreno.movies.data.repository.RegionRepository
import soy.gabimoreno.movies.databinding.FragmentDetailBinding
import soy.gabimoreno.movies.model.AndroidPermissionChecker
import soy.gabimoreno.movies.model.PlayServicesLocationDataSource
import soy.gabimoreno.movies.model.db.RoomDataSource
import soy.gabimoreno.movies.model.server.MovieDbDataSource
import soy.gabimoreno.movies.usecases.FindMovieById
import soy.gabimoreno.movies.usecases.ToggleMovieFavorite

class DetailFragment : Fragment() {

    private val vm: DetailViewModel by lazy {
        getViewModel {
            val movieId = args.movieId

            val moviesRepository = MoviesRepository(
                RoomDataSource(app.db),
                MovieDbDataSource(),
                RegionRepository(
                    PlayServicesLocationDataSource(app),
                    AndroidPermissionChecker(app)
                ),
                app.getString(R.string.api_key)
            )

            DetailViewModel(
                movieId,
                FindMovieById(moviesRepository),
                ToggleMovieFavorite(moviesRepository)
            )
        }
    }
    private var binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            vm = this@DetailFragment.vm
            lifecycleOwner = this@DetailFragment
        }
    }
}
