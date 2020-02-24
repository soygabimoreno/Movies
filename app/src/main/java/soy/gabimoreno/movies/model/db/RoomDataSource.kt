package soy.gabimoreno.movies.model.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import soy.gabimoreno.movies.data.source.LocalDataSource
import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.model.toDbMovie
import soy.gabimoreno.movies.model.toMovie
import soy.gabimoreno.movies.model.db.DbMovie as DbMovie

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty() = withContext(Dispatchers.IO) {
        movieDao.movieCount() <= 0
    }

    override suspend fun getPopularMovies() = withContext(Dispatchers.IO) {
        movieDao.getAll().map(DbMovie::toMovie)
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        withContext(Dispatchers.IO) {
            movieDao.insert(movies.map(Movie::toDbMovie))
        }
    }

    override suspend fun findById(id: Int) = withContext(Dispatchers.IO) {
        movieDao.findById(id).toMovie()
    }

    override suspend fun update(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.update(movie.toDbMovie())
    }
}
