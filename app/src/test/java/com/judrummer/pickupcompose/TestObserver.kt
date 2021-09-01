package com.judrummer.pickupcompose

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {
    private val mutableValues = mutableListOf<T>()

    val values: List<T> = mutableValues

    override fun onChanged(t: T) {
        mutableValues.add(t)
    }
}