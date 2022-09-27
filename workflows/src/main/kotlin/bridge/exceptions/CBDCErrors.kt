package com.cbdc.industria.tech.bridge.exceptions

import java.time.Instant
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CBDCBridgeInternalServerError(
    val statusCode: Int,
    val statusDescription: String,
    val timestamp: String = Instant.now().toString(),
    val description: String? = null,
    override val message: String
) : Exception()

@CordaSerializable
data class CBDCBridgeException(
    val statusCode: Int,
    val name: String,
    override val message: String
) : Exception()
