package soy.gabimoreno.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.bindingInflate
import soy.gabimoreno.movies.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val vm: DetailViewModel by currentScope.viewModel(this) {
        val movieId = args.movieId
        parametersOf(movieId)
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
