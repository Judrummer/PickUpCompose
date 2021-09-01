package com.judrummer.pickupcompose.common.util

import com.judrummer.pickupcompose.location.PickUpLatLng
import com.judrummer.pickupcompose.location.distanceMeterTo
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*

import org.junit.Test
import kotlin.math.roundToInt

class PickUpLatLngKtTest {

    @Test
    fun distanceMeterTo() {
        val centralWorld = PickUpLatLng(13.682860, 100.380151)
        val allSeason = PickUpLatLng(13.739272, 100.548268)
        centralWorld.distanceMeterTo(allSeason).roundToInt() shouldBeEqualTo 19210
    }
}