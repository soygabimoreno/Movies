package soy.gabimoreno.movies.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
