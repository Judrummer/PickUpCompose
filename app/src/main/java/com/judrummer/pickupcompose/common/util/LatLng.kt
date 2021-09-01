package com.judrummer.pickupcompose.common.util

import kotlin.math.*

data class PickUpLatLng(
    val latitude: Double,
    val longitude: Double,
)

//http://www.codecodex.com/wiki/Calculate_Distance_Between_Two_Points_on_a_Globe
fun PickUpLatLng.distanceMeterTo(other: PickUpLatLng): Float {
    val earthRadius = 3958.75
    val latDiff = Math.toRadians((other.latitude - this.latitude))
    val lngDiff = Math.toRadians((other.longitude - this.longitude))
    val a = sin(latDiff / 2) * sin(latDiff / 2) +
            cos(Math.toRadians(this.latitude)) * cos(Math.toRadians(other.latitude)) *
            sin(lngDiff / 2) * sin(lngDiff / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = earthRadius * c
    val meterConversion = 1609
    return (distance * meterConversion.toFloat()).toFloat()
}