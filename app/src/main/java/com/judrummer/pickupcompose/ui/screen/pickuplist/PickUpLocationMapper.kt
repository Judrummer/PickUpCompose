package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.data.GetPickUpLocationsApiResponseEntity

interface PickUpLocationMapper {
    operator fun invoke(entity: GetPickUpLocationsApiResponseEntity.Pickup): PickUpLocation
}

class PickUpLocationMapperImpl : PickUpLocationMapper {
    override fun invoke(entity: GetPickUpLocationsApiResponseEntity.Pickup): PickUpLocation {
        TODO("Not yet implemented")
    }
}