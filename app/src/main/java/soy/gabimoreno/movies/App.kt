package soy.gabimoreno.movies

import android.app.Application
import androidx.room.Room
import soy.gabimoreno.movies.di.DaggerMoviesComponent
import soy.gabimoreno.movies.di.MoviesComponent
import soy.gabimoreno.movies.model.db.MovieDatabase

class App : Application() {

    lateinit var component: MoviesComponent
        private set

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()

        component = DaggerMoviesComponent
            .factory()
            .create(this)
        initDI()
    }
}
