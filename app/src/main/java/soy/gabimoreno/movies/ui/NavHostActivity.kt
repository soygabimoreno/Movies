package soy.gabimoreno.movies.ui

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import soy.gabimoreno.movies.R

class NavHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val newKeyguardLock = keyguardManager.newKeyguardLock("NavHostActivity")
        newKeyguardLock.disableKeyguard()

        setContentView(R.layout.activity_nav_host)
    }
}
