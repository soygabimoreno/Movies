package soy.gabimoreno.movies.model.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import soy.gabimoreno.movies.App
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.RegionRepository
import soy.gabimoreno.movies.model.db.Movie

class MoviesRepository(app: App) {

    private val apiKey = app.getString(R.string.api_key)
    private val regionRepository = RegionRepository(app)
    private val db = app.db

    suspend fun findPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        with(db.movieDao()) {
            if (movieCount() <= 0) {
                val movies = MovieDb.service
                    .listPopularMoviesAsync(
                        apiKey,
                        regionRepository.findLastRegion()
                    )
                    .await()
                    .results

                insert(movies.map(ServerMovie::convertToMovie))
            }
            getAll()
        }
    }

    suspend fun findById(id: Int) = withContext(Dispatchers.IO) {
        db.movieDao().findById(id)
    }

    suspend fun update(movie: Movie) = withContext(Dispatchers.IO) {
        db.movieDao().update(movie)
    }
}

private fun ServerMovie.convertToMovie() = Movie(
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)
