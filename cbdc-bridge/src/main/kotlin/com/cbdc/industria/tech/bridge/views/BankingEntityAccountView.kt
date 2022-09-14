package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.enums.AccountStatus
import com.cbdc.industria.tech.bridge.models.Currency
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class BankingEntityAccountView(
    val id: Long,
    val status: AccountStatus,
    val accountOwningPartyId: Long,
    val openingDate: String,
    val currency: Currency,
    val balance: Long,
    val formattedBalance: String
)
