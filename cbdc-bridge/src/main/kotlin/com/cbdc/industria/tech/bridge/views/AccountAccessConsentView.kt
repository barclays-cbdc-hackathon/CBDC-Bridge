package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.data.BankingEntityWhereRequestingPartyIsRegisteredRef
import com.cbdc.industria.tech.bridge.data.RequestingBankingEntityRef
import com.cbdc.industria.tech.bridge.data.RequestingPartyId
import com.cbdc.industria.tech.bridge.enums.AccessConsentStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class AccountAccessConsentView(
    val accountAccessConsentId: Long,
    val status: AccessConsentStatus,
    val consentRequestingPartyId: RequestingPartyId,
    val consentRequestingBankingEntityRef: RequestingBankingEntityRef,
    val bankingEntityWhereRequestingPartyIsRegisteredRef: BankingEntityWhereRequestingPartyIsRegisteredRef
)
