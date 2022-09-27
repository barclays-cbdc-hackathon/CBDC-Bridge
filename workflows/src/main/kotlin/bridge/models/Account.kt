package com.cbdc.industria.tech.bridge.models

import com.cbdc.industria.tech.bridge.enums.AccountStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class Account(val accountId: Long, val status: AccountStatus)
