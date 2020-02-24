package soy.gabimoreno.movies.model.db

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM DbMovie")
    fun getAll(): List<DbMovie>

    @Query("SELECT * FROM DbMovie WHERE id = :id")
    fun findById(id: Int): DbMovie

    @Query("SELECT COUNT(id) FROM DbMovie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dbMovies: List<DbMovie>)

    @Update
    fun update(dbMovie: DbMovie)
}
