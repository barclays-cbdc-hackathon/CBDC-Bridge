package com.cbdc.industria.tech.bridge.enums

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class PartyType {
    INDIVIDUAL, PUBLIC_LIMITED_COMPANY, PRIVATE_LIMITED_COMPANY
}
