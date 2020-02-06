package soy.gabimoreno.movies.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import soy.gabimoreno.movies.R
import soy.gabimoreno.movies.model.MovieDb
import soy.gabimoreno.movies.ui.common.CoroutineScopeActivity
import kotlin.coroutines.resume

class MainActivity : CoroutineScopeActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var activity: MainActivity

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this@MainActivity

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        launch {
            val location = getLocation()
            val movies = MovieDb.service
                .listPopularMoviesAsync(
                    getString(R.string.api_key),
                    getRegionFromLocation(location)
                )
                .await()
            adapter.movies = movies.results
        }

        rv.adapter = adapter
    }

    private suspend fun getLocation(): Location? {
        val success = requestCoarseLocationPermission()
        return if (success) findLastLocation() else null
    }

    private suspend fun requestCoarseLocationPermission(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            Dexter
                .withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation.resume(false)
                    }
                }
                ).check()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(activity)
        val fromLocation = location?.let {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        }
        return fromLocation?.firstOrNull()?.countryCode ?: "US"
    }
}
