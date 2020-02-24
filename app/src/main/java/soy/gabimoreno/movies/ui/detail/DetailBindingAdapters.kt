package soy.gabimoreno.movies.ui.detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.domain.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieInfo(movie: Movie?) {
    movie?.let {
        setMovie(movie)
    }
}

@BindingAdapter("favorite")
fun ImageView.setFavorite(favorite: Boolean) {
    val iconResId = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
    setImageDrawable(context.getDrawable(iconResId))
}
