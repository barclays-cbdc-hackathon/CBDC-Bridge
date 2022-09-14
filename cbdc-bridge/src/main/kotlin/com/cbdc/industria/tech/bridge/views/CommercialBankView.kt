package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.enums.CurrencyStatus
import com.cbdc.industria.tech.bridge.models.Account
import com.cbdc.industria.tech.bridge.models.Currency
import com.cbdc.industria.tech.bridge.models.RegisteredParty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CommercialBankView(
    val id: Long,
    val status: CurrencyStatus,
    val currency: Currency,
    val commercialBankName: String,
    val parties: Set<RegisteredParty>,
    val accounts: Set<Account>
)
