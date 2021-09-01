package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.location.PickUpLatLng
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*

import org.junit.Test

class PickUpListViewStateTest {

    val centralWorld = PickUpLocation(
        name = "Central world",
        address = "Store",
        city = "Pathum Wan",
        latLng = PickUpLatLng(
            latitude = 13.747312,
            longitude = 100.539631,
        ),
    )
    val allSeason = PickUpLocation(
        name = "All Seasons",
        address = "Pin",
        city = "Pathum Wan",
        latLng = PickUpLatLng(
            latitude = 13.739272,
            longitude = 100.548268,
        ),
    )

    @Test
    fun getCalculatedItems_noCurrentLocation() {
        val state = PickUpListViewState(
            items = listOf(centralWorld, allSeason),
            currentLatLng = null,
        )

        state.calculatedItems shouldBeEqualTo listOf(centralWorld, allSeason)
    }

    @Test
    fun getCalculatedItems_haveCurrentLocationAtAllSeason() {
        val state = PickUpListViewState(
            items = listOf(centralWorld, allSeason),
            currentLatLng = PickUpLatLng(
                latitude = 13.739272,
                longitude = 100.548268
            ),
        )

        state.calculatedItems shouldBeEqualTo listOf(allSeason, centralWorld)
    }

    @Test
    fun getCalculatedItems_haveCurrentLocationAtCentralWorld() {
        val state = PickUpListViewState(
            items = listOf(centralWorld, allSeason),
            currentLatLng = PickUpLatLng(
                latitude = 13.747312,
                longitude = 100.539631,
            ),
        )

        state.calculatedItems shouldBeEqualTo listOf(centralWorld, allSeason)
    }
}