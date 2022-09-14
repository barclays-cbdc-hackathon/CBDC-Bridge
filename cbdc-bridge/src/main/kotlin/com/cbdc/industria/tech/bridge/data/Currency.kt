package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.enums.CurrencyCode
import com.cbdc.industria.tech.bridge.views.CurrencyView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CreateCurrencyRequestBody(val currencyCode: CurrencyCode)

@CordaSerializable
data class CreateCurrencyResponseBody(val data: CreateCurrencyResponseBodyData)

@CordaSerializable
data class CreateCurrencyResponseBodyData(val id: Long)

@CordaSerializable
data class GetCurrencyDetailsResponseBody(val data: CurrencyView)

@CordaSerializable
data class GetCurrencyDetailsPageResponseBody(
    val data: List<CurrencyView>,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
    val pageIndex: Int,
    val pageNumberOfElements: Int
)
