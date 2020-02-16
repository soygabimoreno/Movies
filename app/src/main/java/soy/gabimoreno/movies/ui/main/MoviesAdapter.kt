package soy.gabimoreno.movies.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.common.basicDiffUtil
import soy.gabimoreno.movies.common.bindingInflate
import soy.gabimoreno.movies.databinding.ViewMovieBinding
import soy.gabimoreno.movies.model.db.Movie

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areContentsTheSame = { old, new ->
            old.id == new.id
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bindingInflate(R.layout.view_movie, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.dataBinding.movie = movie
        holder.itemView.setOnClickListener {
            listener(movie)
        }
    }

    class ViewHolder(val dataBinding: ViewMovieBinding) : RecyclerView.ViewHolder(dataBinding.root)
}
