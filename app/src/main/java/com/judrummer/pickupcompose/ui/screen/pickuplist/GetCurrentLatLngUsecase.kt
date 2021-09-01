package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.location.LocationApi
import com.judrummer.pickupcompose.location.PickUpLatLng

interface GetCurrentLatLngUsecase {
    suspend operator fun invoke(): PickUpLatLng?
}

class GetCurrentLatLngUsecaseImpl(
    private val locationApi: LocationApi,
) : GetCurrentLatLngUsecase {
    override suspend operator fun invoke(): PickUpLatLng? = locationApi.getCurrentLatLng()
}