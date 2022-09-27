package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.enums.AccountStatus
import com.cbdc.industria.tech.bridge.models.Currency
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CBDCAccountView(
    val id: Int,
    val accountOwningPIPId: Int,
    val status: AccountStatus,
    val openingDate: String,
    val currency: Currency,
    val balance: Int,
    val formattedBalance: String
)
