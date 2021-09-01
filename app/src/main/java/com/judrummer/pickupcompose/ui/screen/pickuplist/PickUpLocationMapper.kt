package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.util.PickUpLatLng
import com.judrummer.pickupcompose.data.GetPickUpLocationsApiResponseEntity

interface PickUpLocationMapper {
    operator fun invoke(entity: GetPickUpLocationsApiResponseEntity.Pickup): PickUpLocation
}

class PickUpLocationMapperImpl : PickUpLocationMapper {
    override fun invoke(entity: GetPickUpLocationsApiResponseEntity.Pickup): PickUpLocation {
        return PickUpLocation(
            name = entity.alias.orEmpty(),
            address = entity.address1.orEmpty(),
            city = entity.city.orEmpty(),
            latLng = PickUpLatLng(
                entity.latitude ?: 0.0,
                entity.longitude ?: 0.0,
            ),
        )
    }
}