package soy.gabimoreno.movies.model.db

import soy.gabimoreno.movies.data.LocalDataSource
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.model.db.Movie as DbMovie

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override fun isEmpty(): Boolean = movieDao.movieCount() <= 0

    override fun getPopularMovies(): List<Movie> {
        return movieDao.getAll().map(DbMovie::toMovie)
    }

    override fun saveMovies(movies: List<Movie>) {
        movieDao.insert(movies.map(Movie::toDbMovie))
    }
}

fun Movie.toDbMovie(): DbMovie = DbMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

fun DbMovie.toMovie(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)
