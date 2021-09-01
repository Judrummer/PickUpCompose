package com.judrummer.pickupcompose.ui.screen.pickuplist

import com.judrummer.pickupcompose.common.util.PickUpLatLng
import com.judrummer.pickupcompose.data.GetPickUpLocationsApiResponseEntity
import com.judrummer.pickupcompose.data.PickUpApi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*

import org.junit.Test


class TestThrowable : Throwable()

class GetActivePickUpLocationsUsecaseImplTest {

    val mapper: PickUpLocationMapper = mockk(relaxed = true)
    val api: PickUpApi = mockk(relaxed = true)
    val usecase = GetActivePickUpLocationsUsecaseImpl(api, mapper)

    @Test
    fun invoke_success() {
        runBlocking {
            val entity1 = GetPickUpLocationsApiResponseEntity.Pickup(
                alias = "Central world",
                address1 = "Store",
                city = "Pathum Wan",
                active = true,
                latitude = 13.747312,
                longitude = 100.539631,
            )

            val entity2 = GetPickUpLocationsApiResponseEntity.Pickup(
                alias = "All Seasons",
                address1 = "Pin",
                city = "Pathum Wan",
                active = true,
                latitude = 13.739272,
                longitude = 100.548268,
            )

            val entity3 = GetPickUpLocationsApiResponseEntity.Pickup(
                alias = "INTERCHANGE 21",
                address1 = "Pin",
                city = "Watthana",
                active = false,
                latitude = 12.123,
                longitude = 100.400,
            )

            val entity4 = GetPickUpLocationsApiResponseEntity.Pickup(
                alias = null,
                address1 = null,
                city = null,
                active = true,
                latitude = 12.123,
                longitude = 100.400,
            )

            val item1 = PickUpLocation(
                name = "Central world",
                address = "Store",
                city = "Pathum Wan",
                latLng = PickUpLatLng(
                    latitude = 13.747312,
                    longitude = 100.539631,
                ),
            )

            val item2 = PickUpLocation(
                name = "All Seasons",
                address = "Pin",
                city = "Pathum Wan",
                latLng = PickUpLatLng(
                    latitude = 13.739272,
                    longitude = 100.548268,
                ),
            )

            val responseEntity = GetPickUpLocationsApiResponseEntity(
                pickup = listOf(entity1, entity2, entity3, entity4)
            )

            coEvery { api.getPickUpLocations() } returns responseEntity
            every { mapper(entity1) } returns item1
            every { mapper(entity2) } returns item2

            val result = usecase()

            verify(exactly = 0) { mapper(entity3) }
            verify(exactly = 0) { mapper(entity4) }
            result shouldBeEqualTo listOf(item1, item2)
        }
    }

    @Test(expected = TestThrowable::class)
    fun invoke_error() {
        runBlocking {
            val error = TestThrowable()

            coEvery { api.getPickUpLocations() } throws error

            usecase()
        }
    }

}