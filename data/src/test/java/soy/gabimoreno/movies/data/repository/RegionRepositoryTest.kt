package soy.gabimoreno.movies.data.repository

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import soy.gabimoreno.movies.data.PermissionChecker
import soy.gabimoreno.movies.data.source.LocationDataSource

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(
            locationDataSource,
            permissionChecker
        )
    }

    @Test
    fun `returns default when coarse permission not granted`() {
        runBlocking {
            val defaultRegion = RegionRepository.DEFAULT_REGION
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(false)

            val result = regionRepository.findLastRegion()

            assertEquals(defaultRegion, result)
        }
    }
}
