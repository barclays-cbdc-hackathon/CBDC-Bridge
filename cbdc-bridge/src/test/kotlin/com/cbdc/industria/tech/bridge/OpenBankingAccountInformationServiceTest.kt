package com.cbdc.industria.tech.bridge

import com.cbdc.industria.tech.bridge.data.OpenBankingAccountAccessConsentCreationRequestBody
import com.cbdc.industria.tech.bridge.enums.CurrencyCode
import com.cbdc.industria.tech.bridge.services.CurrencyService
import com.cbdc.industria.tech.bridge.services.HOST_URL
import com.cbdc.industria.tech.bridge.services.OpenBankingAccountInformationService
import com.cbdc.industria.tech.bridge.services.SandboxEnvService
import com.cbdc.industria.tech.bridge.services.THREADS_COUNT
import java.util.concurrent.Executors
import net.corda.core.utilities.getOrThrow
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OpenBankingAccountInformationServiceTest {
    private val sandboxEnvService = SandboxEnvService(
        executor = Executors.newFixedThreadPool(THREADS_COUNT),
        host = HOST_URL
    )

    private val currenciesService = CurrencyService(
        executor = Executors.newFixedThreadPool(THREADS_COUNT),
        host = HOST_URL
    )

    private val openBankingAccountInformationService = OpenBankingAccountInformationService(
        executor = Executors.newFixedThreadPool(THREADS_COUNT),
        host = HOST_URL
    )

    private var environmentId: Long = 0
    private var currencyId: Long = 0

    @BeforeEach
    fun setup() {
        environmentId = sandboxEnvService.createEnvironment()
        currencyId = currenciesService.createCurrency(CurrencyCode.EUR, environmentId)
    }

    @AfterEach
    fun tearDown() {
        currenciesService.deleteCurrency(xEnvId = environmentId, currencyId = currencyId)
        sandboxEnvService.deleteEnv(environmentId)
    }


    @Test
    fun `account access consents`() {
//        openBankingAccountInformationService.createAccountAccessConsent(
//            xEnvId = environmentId,
//            currencyId = currencyId,
//            body = OpenBankingAccountAccessConsentCreationRequestBody()
//        )
//        sandboxEnvService.deleteEnv(env.data.id).getOrThrow()
    }
}