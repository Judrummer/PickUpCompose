package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.data.PickUpApi
import com.judrummer.pickupcompose.location.PickUpLatLng

data class PickUpLocation(
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val latLng: PickUpLatLng = PickUpLatLng(0.0, 0.0),
)

interface GetActivePickUpLocationsUsecase {
    suspend operator fun invoke(): List<PickUpLocation>
}

class GetActivePickUpLocationsUsecaseImpl(
    private val pickUpApi: PickUpApi,
    private val mapper: PickUpLocationMapper,
) : GetActivePickUpLocationsUsecase {
    override suspend operator fun invoke(): List<PickUpLocation> = pickUpApi.getPickUpLocations().pickup.orEmpty()
        .filter { it.active == true && it.alias?.isNotBlank() == true } //Filter empty alias because it's look nonsense to show blank card
        .map(mapper::invoke)
}