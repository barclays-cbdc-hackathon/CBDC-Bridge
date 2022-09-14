package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.enums.DomesticPaymentStatus
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class MakeDomesticPaymentRequestBody(
    val sourceAccountId: Long,
    val destinationAccountId: Long,
    val paymentAmountInCurrencyUnits: Long
)

@CordaSerializable
data class MakeDomesticPaymentResponseBody(val data: MakeDomesticPaymentResponseData)

@CordaSerializable
data class 	MakeDomesticPaymentResponseData(val id: Long)

@CordaSerializable
data class GetDomesticPaymentDetailsResponseBody(val data: DomesticPaymentView)

@CordaSerializable
data class 	DomesticPaymentView(
    val paymentId: Long,
    val paymentInitiationDetails: PaymentInitiationDetails,
    val status: DomesticPaymentStatus,
    val failureReason: String
    )

@CordaSerializable
data class PaymentInitiationDetails(
    val sourceAccountId: Long,
    val destinationAccountId: Long,
    val paymentAmount: Long
)