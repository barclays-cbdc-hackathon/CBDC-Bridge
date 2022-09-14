package com.cbdc.industria.tech.bridge.enums

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class AccountStatus {
    OPEN,
    CLOSED
}
