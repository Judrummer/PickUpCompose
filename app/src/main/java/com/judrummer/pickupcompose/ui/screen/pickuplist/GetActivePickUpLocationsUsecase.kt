package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.data.PickUpApi

data class PickUpLocation(
    val id: Long = 0L,
)

interface GetActivePickUpLocationsUsecase {
    suspend operator fun invoke(): List<PickUpLocation>
}

class GetActivePickUpLocationsUsecaseImpl(
    private val pickUpApi: PickUpApi,
    private val mapper: PickUpLocationMapper,
) : GetActivePickUpLocationsUsecase {
    override suspend operator fun invoke(): List<PickUpLocation> = pickUpApi.getPickUpLocations().pickup.orEmpty().map(mapper::invoke)
}