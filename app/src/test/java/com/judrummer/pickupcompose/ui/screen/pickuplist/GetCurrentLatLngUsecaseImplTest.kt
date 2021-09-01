package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.location.LocationApi
import com.judrummer.pickupcompose.location.PickUpLatLng
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetCurrentLatLngUsecaseImplTest {

    val api: LocationApi = mockk(relaxed = true)
    val usecase = GetCurrentLatLngUsecaseImpl(api)

    @Test
    fun invoke_success() {
        runBlocking {
            val location = PickUpLatLng(10.0, 100.523)
            coEvery { api.getCurrentLatLng() } returns location

            val result = usecase()

            result shouldBeEqualTo location
        }
    }

    @Test(expected = TestThrowable::class)
    fun invoke_error() {
        runBlocking {
            val error = TestThrowable()

            coEvery { api.getCurrentLatLng() } throws error

            usecase()
        }
    }
}