package com.cbdc.industria.tech.bridge.models

import com.cbdc.industria.tech.bridge.enums.CurrencyStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PIPChild(val id: Long, val name: String, val status: CurrencyStatus)
