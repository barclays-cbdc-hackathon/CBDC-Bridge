package com.cbdc.industria.tech.bridge.exceptions

import net.corda.core.flows.FlowException
import java.time.Instant

class CBDCBridgeInternalServerError(
    val statusCode: Int,
    val statusDescription: String,
    val timestamp: String = Instant.now().toString(),
    val description: String? = null,
    override val message: String
) : FlowException()

class CBDCBridgeException(
    val statusCode: Int,
    val name: String,
    override val message: String
) : FlowException()
