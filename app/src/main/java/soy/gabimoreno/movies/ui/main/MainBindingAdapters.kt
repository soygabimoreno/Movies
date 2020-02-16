package soy.gabimoreno.movies.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import soy.gabimoreno.movies.model.db.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(items: List<Movie>?) {
    (adapter as? MoviesAdapter)?.let {
        it.movies = items ?: emptyList()
    }
}
