package soy.gabimoreno.movies.model

import soy.gabimoreno.movies.domain.Movie
import soy.gabimoreno.movies.model.db.DbMovie
import soy.gabimoreno.movies.model.server.ServerMovie

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

fun ServerMovie.toMovie(): Movie =
    Movie(
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
