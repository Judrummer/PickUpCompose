package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.base.StateViewModel
import com.judrummer.pickupcompose.common.util.PickUpLatLng
import com.judrummer.pickupcompose.common.util.distanceMeterTo
import kotlinx.coroutines.launch

data class PickUpListViewState(
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val error: Throwable? = null,
    val items: List<PickUpLocation> = emptyList(),
    val currentLatLng: PickUpLatLng? = null,
) {
    val calculatedItems = currentLatLng
        ?.let { items.sortedBy { item -> item.latLng.distanceMeterTo(it) } }
        ?: items
}

class PickUpListViewModel(
    private val getActivePickUpLocationsUsecase: GetActivePickUpLocationsUsecase
) : StateViewModel<PickUpListViewState>(PickUpListViewState()) {
    override fun onInitialize() {
        setState { copy(loading = true) }
        fetch()
    }

    fun onRefresh() {
        setState { copy(refreshing = true) }
        fetch()
    }

    fun setCurrentLatLng(value: PickUpLatLng) {
        setState { copy(currentLatLng = value) }
    }

    private fun fetch() {
        launch {
            try {
                val items = getActivePickUpLocationsUsecase()
                setState { copy(refreshing = false, loading = false, items = items) }
            } catch (e: Throwable) {
                setState { copy(refreshing = false, loading = false, error = e) }
            }
        }
    }
}