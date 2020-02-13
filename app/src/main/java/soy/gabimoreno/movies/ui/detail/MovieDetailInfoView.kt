package soy.gabimoreno.movies.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import soy.gabimoreno.movies.model.db.Movie
import soy.gabimoreno.movies.model.server.ServerMovie

class MovieDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun setMovie(movie: Movie) = with(movie) {
        text = buildSpannedString {
            bold { append("Original language: ") }
            appendln(originalLanguage)

            bold { append("Original title: ") }
            appendln(originalTitle)

            bold { append("Release date: ") }
            appendln(releaseDate)

            bold { append("Popularity: ") }
            appendln(popularity.toString())

            bold { append("Vote Average: ") }
            append(voteAverage.toString())
        }
    }
}