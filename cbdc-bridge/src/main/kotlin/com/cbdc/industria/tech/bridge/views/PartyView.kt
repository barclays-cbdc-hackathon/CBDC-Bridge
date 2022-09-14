package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.enums.PartyStatus
import com.cbdc.industria.tech.bridge.enums.PartyType
import com.cbdc.industria.tech.bridge.models.OwnedAccount
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PartyView(
    val id: Long,
    val status: PartyStatus,
    val type: PartyType,
    val fullLegalName: String,
    val postalAddress: String,
    val accountsOwned: Set<OwnedAccount>
)
