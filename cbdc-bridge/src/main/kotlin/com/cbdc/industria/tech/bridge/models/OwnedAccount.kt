package com.cbdc.industria.tech.bridge.models

import com.cbdc.industria.tech.bridge.enums.AccountStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class OwnedAccount(val accountId: Int, val accountStatus: AccountStatus)
