package com.judrummer.pickupcompose.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class StateViewModel<T : Any>(initialState: T) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    protected val mutableState = MutableLiveData(initialState)

    val state: LiveData<T> = mutableState

    val currentState: T get() = state.value!!

    private var isInitializeAlready = false

    fun initialize() {
        if (!isInitializeAlready) {
            isInitializeAlready = true
            onInitialize()
        }
    }

    protected abstract fun onInitialize()

    protected inline fun setState(builder: T.() -> T) {
        mutableState.value = state.value!!.builder()
    }
}