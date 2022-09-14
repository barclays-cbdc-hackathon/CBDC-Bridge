package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.models.CurrencyChild
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class EnvironmentView(
    val id: Long,
    val status: String,
    val currencies: List<CurrencyChild>
)
