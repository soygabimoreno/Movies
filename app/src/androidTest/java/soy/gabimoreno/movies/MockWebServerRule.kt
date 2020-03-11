package soy.gabimoreno.movies

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule: TestRule {

    val server = MockWebServer()

    override fun apply(base: Statement?, description: Description?) = object: Statement() {
        override fun evaluate() {
            server
        }
    }
}