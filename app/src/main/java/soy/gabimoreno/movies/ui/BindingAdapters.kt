package soy.gabimoreno.movies.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import soy.gabimoreno.movies.common.gone
import soy.gabimoreno.movies.common.loadUrl
import soy.gabimoreno.movies.common.setVisibleOrGone

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    url?.let {
        loadUrl(url)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visible?.let {
        setVisibleOrGone(visible)
    } ?: gone()
}
