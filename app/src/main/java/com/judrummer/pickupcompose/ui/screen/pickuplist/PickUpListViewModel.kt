package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.base.StateViewModel
import com.judrummer.pickupcompose.location.PickUpLatLng
import com.judrummer.pickupcompose.location.distanceMeterTo
import kotlinx.coroutines.launch

data class PickUpListViewState(
    val loading: Boolean = true,
    val loadingLocation: Boolean = false,
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
    private val getActivePickUpLocationsUsecase: GetActivePickUpLocationsUsecase,
    private val getCurrentLatLngUsecase: GetCurrentLatLngUsecase,
) : StateViewModel<PickUpListViewState>(PickUpListViewState()) {
    override fun onInitialize() {
        setState { copy(loading = true) }
        fetch()
    }

    fun onRefresh() {
        setState { copy(refreshing = true) }
        fetch()
    }

    fun requestLocation() {
        launch {
            setState { copy(loadingLocation = true) }
            try {
                val latLng = getCurrentLatLngUsecase()
                setState { copy(loadingLocation = false, currentLatLng = latLng) }
            } catch (e: Throwable) {
                setState { copy(loadingLocation = false) }
            }
        }
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