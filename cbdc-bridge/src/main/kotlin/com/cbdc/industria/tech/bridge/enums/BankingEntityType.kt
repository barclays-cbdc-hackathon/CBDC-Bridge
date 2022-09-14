package com.cbdc.industria.tech.bridge.enums

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class BankingEntityType {
    PAYMENT_INTERFACE_PROVIDER, COMMERCIAL_BANK
}
