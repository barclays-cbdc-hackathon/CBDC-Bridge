package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.enums.BankingEntityType
import com.cbdc.industria.tech.bridge.views.AccountAccessConsentView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class OpenBankingAccountAccessConsentCreationRequestBody(
    val requestingPartyId: RequestingPartyId,
    val bankingEntityWhereRequestingPartyIsRegisteredRef: BankingEntityWhereRequestingPartyIsRegisteredRef,
    val requestingBankingEntityRef: RequestingBankingEntityRef
)

@CordaSerializable
data class RequestingPartyId(val partyId: Long)

@CordaSerializable
data class BankingEntityWhereRequestingPartyIsRegisteredRef(
    val bankingEntityType: BankingEntityType,
    val bankingEntityId: Long
)

@CordaSerializable
data class RequestingBankingEntityRef(
    val bankingEntityType: BankingEntityType,
    val bankingEntityId: Long
)

@CordaSerializable
data class CreateAccountAccessConsentResponseBody(
    val data: CreateAccountAccessConsentResponseBodyData
)

@CordaSerializable
data class CreateAccountAccessConsentResponseBodyData(
    val id: Long
)

@CordaSerializable
data class GetAccountAccessConsentResponseBody(val data: AccountAccessConsentView)
