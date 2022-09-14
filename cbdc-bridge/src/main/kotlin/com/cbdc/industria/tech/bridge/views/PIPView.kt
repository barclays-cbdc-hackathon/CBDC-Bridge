package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.models.Account
import com.cbdc.industria.tech.bridge.models.Currency
import com.cbdc.industria.tech.bridge.models.RegisteredParty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PIPView(
    val id: Long,
    val status: String,
    val currency: Currency,
    val pipName: String,
    val parties: List<RegisteredParty>,
    val accounts: List<Account>
)
