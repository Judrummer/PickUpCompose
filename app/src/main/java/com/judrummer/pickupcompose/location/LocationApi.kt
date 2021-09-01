package com.judrummer.pickupcompose.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

interface LocationApi {
    suspend fun getCurrentLatLng(): PickUpLatLng?
}

class LocationApiImpl(
    context: Context,
) : LocationApi {

    private val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLatLng(): PickUpLatLng? = locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null).await()?.run {
        PickUpLatLng(latitude, longitude)
    }
}