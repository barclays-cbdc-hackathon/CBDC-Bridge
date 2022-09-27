package com.cbdc.industria.tech.bridge.enums

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class PaymentConsentStatus {
    AWAITING_AUTHORISATION, REJECTED, AUTHORISED, CONSUMED
}
