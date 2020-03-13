package soy.gabimoreno.movies

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
