package com.judrummer.pickupcompose

import org.amshove.kluent.internal.assertEquals

fun <T> TestObserver<T>.assertState(initialState: T, vararg nextStateReducers: T.() -> T) {
    val expected = nextStateReducers.fold(listOf(initialState)) { currentStates, reducer -> currentStates + currentStates.last().reducer() }

    values.zip(expected) { v, e -> v to e }.forEachIndexed { index, (actual, expected) ->
        assertEquals(expected, actual, "State[$index] not matches $actual != $expected")
    }
}