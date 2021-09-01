package com.judrummer.pickupcompose.ui.screen.pickuplist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.judrummer.pickupcompose.TestDispatcher
import com.judrummer.pickupcompose.TestObserver
import com.judrummer.pickupcompose.assertState
import com.judrummer.pickupcompose.location.PickUpLatLng
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PickUpListViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val getActivePickUpLocationsUsecase: GetActivePickUpLocationsUsecase = mockk(relaxed = true)
    val getCurrentLatLngUsecase: GetCurrentLatLngUsecase = mockk(relaxed = true)
    val stateObserver = TestObserver<PickUpListViewState>()

    val viewModel = PickUpListViewModel(getActivePickUpLocationsUsecase, getCurrentLatLngUsecase)

    @Before
    fun setup() {
        Dispatchers.setMain(TestDispatcher)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun initialize_success() {
        val items = listOf(
            PickUpLocation(
                name = "Central world",
                address = "Store",
                city = "Pathum Wan",
                latLng = PickUpLatLng(
                    latitude = 13.747312,
                    longitude = 100.539631,
                ),
            )
        )
        coEvery { getActivePickUpLocationsUsecase() } returns items

        viewModel.initialize()

        stateObserver.assertState(
            PickUpListViewState(),
            {
                copy(loading = true)
            },
            {
                copy(
                    loading = false,
                    error = null,
                    items = items,
                )
            },
        )
    }

    @Test
    fun initialize_error() {
        val error = Throwable("Mock Error")
        coEvery { getActivePickUpLocationsUsecase() } throws error

        viewModel.initialize()

        stateObserver.assertState(
            PickUpListViewState(),
            {
                copy(loading = true)
            },
            {
                copy(
                    loading = false,
                    error = error,
                    items = emptyList(),
                )
            },
        )
    }

    @Test
    fun refresh_success() {
        val items = listOf(
            PickUpLocation(
                name = "Central world",
                address = "Store",
                city = "Pathum Wan",
                latLng = PickUpLatLng(
                    latitude = 13.747312,
                    longitude = 100.539631,
                ),
            )
        )
        coEvery { getActivePickUpLocationsUsecase() } returns items

        viewModel.onRefresh()

        stateObserver.assertState(
            PickUpListViewState(),
            {
                copy(refreshing = true)
            },
            {
                copy(
                    refreshing = false,
                    error = null,
                    items = items,
                )
            },
        )
    }

    @Test
    fun refreshing_error() {
        val error = Throwable("Mock Error")
        coEvery { getActivePickUpLocationsUsecase() } throws error

        viewModel.onRefresh()

        stateObserver.assertState(
            PickUpListViewState(),
            {
                copy(refreshing = true)
            },
            {
                copy(
                    refreshing = false,
                    error = error,
                )
            },
        )
    }

    @Test
    fun requestLocation_success() {
        val location = PickUpLatLng(13.682860, 100.380151)
        coEvery { getCurrentLatLngUsecase() } returns location

        viewModel.requestLocation()

        stateObserver.assertState(
            PickUpListViewState(),
            { copy(loadingLocation = true) },
            { copy(loadingLocation = false, currentLatLng = location) },
        )
    }

    @Test
    fun requestLocation_error() {
        val error = Throwable("Mock Error")
        coEvery { getCurrentLatLngUsecase() } throws error

        viewModel.requestLocation()

        stateObserver.assertState(
            PickUpListViewState(),
            { copy(loadingLocation = true) },
            { copy(loadingLocation = false) },
        )
    }
}