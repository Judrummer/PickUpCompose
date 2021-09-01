package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.util.PickUpLatLng
import com.judrummer.pickupcompose.data.GetPickUpLocationsApiResponseEntity
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*

import org.junit.Test

class PickUpLocationMapperImplTest {

    val mapper = PickUpLocationMapperImpl()

    @Test
    fun map_normalData() {
        val entity = GetPickUpLocationsApiResponseEntity.Pickup(
            alias = "Central world",
            address1 = "Store",
            city = "Pathum Wan",
            latitude = 13.747312,
            longitude = 100.539631,
        )
        val expected = PickUpLocation(
            name = "Central world",
            address = "Store",
            city = "Pathum Wan",
            latLng = PickUpLatLng(
                latitude = 13.747312,
                longitude = 100.539631,
            ),
        )
        mapper(entity) shouldBeEqualTo expected
    }

    @Test
    fun map_nullData() {
        val entity = GetPickUpLocationsApiResponseEntity.Pickup(
            alias = null,
            address1 = null,
            city = null,
            latitude = null,
            longitude = null,
        )
        val expected = PickUpLocation(
            name = "",
            address = "",
            city = "",
            latLng = PickUpLatLng(
                latitude = 0.0,
                longitude = 0.0,
            ),
        )
        mapper(entity) shouldBeEqualTo expected
    }
}