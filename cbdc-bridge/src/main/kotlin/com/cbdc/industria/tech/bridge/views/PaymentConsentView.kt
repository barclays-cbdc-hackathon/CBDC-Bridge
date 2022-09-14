package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.data.BankingEntityWhereRequestingPartyIsRegisteredRef
import com.cbdc.industria.tech.bridge.data.PaymentInitiationDetails
import com.cbdc.industria.tech.bridge.data.RequestingBankingEntityRef
import com.cbdc.industria.tech.bridge.data.RequestingPartyId
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PaymentConsentView(
    val paymentConsentId: String,
    val status: String,
    val consentRequestingPartyId: RequestingPartyId,
    val consentRequestingBankingEntityRef: RequestingBankingEntityRef,
    val bankingEntityWhereRequestingPartyIsRegisteredRef: BankingEntityWhereRequestingPartyIsRegisteredRef,
    val paymentDetails: PaymentInitiationDetails
)
