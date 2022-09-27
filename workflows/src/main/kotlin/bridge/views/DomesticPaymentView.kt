package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.data.PaymentInitiationDetails
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class DomesticPaymentView(
    val paymentId: Long,
    val paymentInitiationDetails: PaymentInitiationDetails,
    val status: String,
    val failureReason: String
)