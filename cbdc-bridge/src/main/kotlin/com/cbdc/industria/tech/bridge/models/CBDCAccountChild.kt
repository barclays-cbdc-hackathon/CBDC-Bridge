package com.cbdc.industria.tech.bridge.models

import com.cbdc.industria.tech.bridge.enums.AccountStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CBDCAccountChild(val id: Int, val accountOwningPIPId: Int, val status: AccountStatus)
