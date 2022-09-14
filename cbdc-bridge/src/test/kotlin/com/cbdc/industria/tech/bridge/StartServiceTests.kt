package com.cbdc.industria.tech.bridge

import com.cbdc.industria.tech.bridge.services.MockStartService
import net.corda.core.utilities.getOrThrow
import org.junit.jupiter.api.Test

class StartServiceTests {

    private val mockStartService = MockStartService()

    @Test
    fun getPublicPing() {
        mockStartService.getPublicPing().getOrThrow()
    }
}