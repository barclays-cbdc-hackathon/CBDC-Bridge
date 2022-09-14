package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.views.EnvironmentView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CreateNewEnvironmentResponseBody(val data: CreateNewEnvironmentResponseData)

@CordaSerializable
data class CreateNewEnvironmentResponseData(val id: Long)

@CordaSerializable
data class GetEnvironmentDetailsResponseBody(val data: EnvironmentView)

@CordaSerializable
data class GetEnvironmentDetailsPageResponseBody(
    val data: List<EnvironmentView>,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
    val pageIndex: Int,
    val pageNumberOfElements: Int
)
