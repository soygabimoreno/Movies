package soy.gabimoreno.movies.model

import android.app.Activity
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

class PlayServicesLocationDataSource(activity: Activity) : LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    override suspend fun findLastLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }
    }
}

class NotAvailableLocationDataSource : LocationDataSource {

    override suspend fun findLastLocation(): Location? {
        throw UnsupportedOperationException("This operation cannot be done in this device")
    }
}
