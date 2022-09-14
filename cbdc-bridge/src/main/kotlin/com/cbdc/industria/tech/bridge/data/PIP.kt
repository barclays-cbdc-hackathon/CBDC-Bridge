package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.views.PIPView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CreatePaymentInterfaceProviderRequestBody(val pipName: String)

@CordaSerializable
data class CreatePaymentInterfaceProviderResponseBody(val data: CreatePaymentInterfaceProviderResponseBodyData)

@CordaSerializable
data class CreatePaymentInterfaceProviderResponseBodyData(val id: Long)

@CordaSerializable
data class GetPIPDetailsResponseBody(val data: PIPView)

@CordaSerializable
data class GetPIPDetailsPageResponseBody(
    val data: List<PIPView>,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
    val pageIndex: Int,
    val pageNumberOfElements: Int
)
