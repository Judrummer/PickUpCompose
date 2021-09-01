package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.base.StateViewModel
import kotlinx.coroutines.launch

data class PickUpListViewState(
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val error: Throwable? = null,
    val items: List<PickUpLocation> = emptyList(),
)

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