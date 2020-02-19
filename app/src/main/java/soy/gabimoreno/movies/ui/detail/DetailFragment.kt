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
import soy.gabimoreno.movies.databinding.FragmentDetailBinding
import soy.gabimoreno.movies.model.server.MoviesRepository

class DetailFragment : Fragment() {

    private val vm: DetailViewModel by lazy {
        getViewModel {
            val movieId = args.movieId
            DetailViewModel(movieId, MoviesRepository(app))
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
