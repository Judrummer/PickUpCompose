package com.judrummer.pickupcompose.ui.screen.pickuplist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.judrummer.pickupcompose.TestDispatcher
import com.judrummer.pickupcompose.TestObserver
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

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


}