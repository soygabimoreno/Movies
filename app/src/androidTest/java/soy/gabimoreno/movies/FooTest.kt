package soy.gabimoreno.movies

import androidx.test.runner.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import soy.gabimoreno.movies.data.source.RemoteDataSource

@RunWith(AndroidJUnit4::class)
class FooTest {

    @Before
    fun setUp() {
        val server = MockWebServer()
        server.start()

        val testModule = module(override = true) {
            factory<RemoteDataSource> { FakeRemoteDataSource() }
        }
        loadKoinModules(testModule)
    }

    @Test
    fun foo() {
    }
}
