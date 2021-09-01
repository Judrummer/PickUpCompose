package com.judrummer.pickupcompose.data

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class GetPickUpLocationsApiResponseEntity(
    @SerialName("number_of_new_locations")
    val numberOfNewLocations: Int? = null,
    @SerialName("pickup")
    val pickup: List<Pickup>? = null
) {
    @Serializable
    data class Pickup(
        @SerialName("active")
        val active: Boolean? = null,
        @SerialName("address1")
        val address1: String? = null,
        @SerialName("address2")
        val address2: String? = null,
        @SerialName("alias")
        val alias: String? = null,
        @SerialName("city")
        val city: String? = null,
        @SerialName("company")
        val company: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("district")
        val district: String? = null,
        @SerialName("feature")
        val feature: String? = null,
        @SerialName("features")
        val features: List<Feature>? = null,
        @SerialName("floor_number")
        val floorNumber: String? = null,
        @SerialName("floormap_image_path")
        val floormapImagePath: String? = null,
        @SerialName("hours")
        val hours: List<String?>? = null,
        @SerialName("hours1")
        val hours1: String? = null,
        @SerialName("hours2")
        val hours2: String? = null,
        @SerialName("hours3")
        val hours3: String? = null,
        @SerialName("id_carrier")
        val idCarrier: Int? = null,
        @SerialName("id_country")
        val idCountry: Int? = null,
        @SerialName("id_partner_store")
        val idPartnerStore: Int? = null,
        @SerialName("id_pickup_location")
        val idPickupLocation: Int? = null,
        @SerialName("id_state")
        val idState: Int? = null,
        @SerialName("id_zone")
        val idZone: Int? = null,
        @SerialName("images")
        val images: Images? = null,
        @SerialName("is_default_location")
        val isDefaultLocation: Boolean? = null,
        @SerialName("is_featured")
        val isFeatured: Boolean? = null,
        @SerialName("is_new_location")
        val isNewLocation: Boolean? = null,
        @SerialName("latitude")
        val latitude: Double? = null,
        @SerialName("longitude")
        val longitude: Double? = null,
        @SerialName("nearest_bts")
        val nearestBts: String? = null,
        @SerialName("nps_link")
        val npsLink: String? = null,
        @SerialName("payment_methods")
        val paymentMethods: List<PaymentMethod>? = null,
        @SerialName("phone")
        val phone: String? = null,
        @SerialName("postcode")
        val postcode: String? = null,
        @SerialName("status")
        val status: String? = null,
        @SerialName("store_image_path")
        val storeImagePath: String? = null,
        @SerialName("subtype")
        val subtype: String? = null,
        @SerialName("type")
        val type: String? = null
    ) {
        @Serializable
        data class Feature(
            @SerialName("description")
            val description: String? = null,
            @SerialName("type")
            val type: String? = null
        )

        @Serializable
        data class Images(
            @SerialName("floormap")
            val floormap: Floormap? = null,
            @SerialName("store")
            val store: Store? = null
        ) {
            @Serializable
            data class Floormap(
                @SerialName("full_main")
                val fullMain: String? = null,
                @SerialName("full_zoomed")
                val fullZoomed: String? = null,
                @SerialName("main")
                val main: String? = null,
                @SerialName("zoomed")
                val zoomed: String? = null
            )

            @Serializable
            data class Store(
                @SerialName("full_secondary")
                val fullSecondary: String? = null,
                @SerialName("primary")
                val primary: Primary? = null,
                @SerialName("secondary")
                val secondary: String? = null
            ) {
                @Serializable
                data class Primary(
                    @SerialName("full_landscape")
                    val fullLandscape: String? = null,
                    @SerialName("full_portrait")
                    val fullPortrait: String? = null,
                    @SerialName("landscape")
                    val landscape: String? = null,
                    @SerialName("portrait")
                    val portrait: String? = null
                )
            }
        }

        @Serializable
        data class PaymentMethod(
            @SerialName("active")
            val active: Int? = null,
            @SerialName("description")
            val description: String? = null,
            @SerialName("id_partner_store")
            val idPartnerStore: Int? = null,
            @SerialName("id_payment_type")
            val idPaymentType: Int? = null,
            @SerialName("is_new")
            val isNew: Boolean? = null,
            @SerialName("position")
            val position: Int? = null
        )
    }
}

interface PickUpApi {
    suspend fun getPickUpLocations(): GetPickUpLocationsApiResponseEntity
}

class PickUpApiImpl(private val httpClient: HttpClient) : PickUpApi {
    override suspend fun getPickUpLocations(): GetPickUpLocationsApiResponseEntity = httpClient.get("https://45434c1b-1e22-4af2-8c9f-c2d99ffa4896.mock.pstmn.io/v3/pickup-locations")
}