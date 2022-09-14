package com.cbdc.industria.tech.bridge

import com.cbdc.industria.tech.bridge.data.CreateCommercialBankRequestBody
import com.cbdc.industria.tech.bridge.data.CreateCurrencyRequestBody
import com.cbdc.industria.tech.bridge.data.GetEnvironmentDetailsPageResponseBody
import com.cbdc.industria.tech.bridge.enums.CurrencyCode
import com.cbdc.industria.tech.bridge.services.CommercialBankService
import com.cbdc.industria.tech.bridge.services.CurrencyService
import com.cbdc.industria.tech.bridge.services.SandboxEnvService
import net.corda.core.utilities.getOrThrow

fun SandboxEnvService.getEnvironments() =
    getEnvs().getOrThrow()

private fun GetEnvironmentDetailsPageResponseBody.withoutTerminated() =
    data.filter { it.status != "TERMINATED" }

fun SandboxEnvService.getEnvsWithoutTerminated() =
    getEnvironments().withoutTerminated()

fun SandboxEnvService.createEnvironment(): Long {
    val envFuture = postEnv()
    val env = envFuture.getOrThrow()
    return env.data.id
}

fun CurrencyService.getAllCurrencies(environmentId: Long) =
    getCurrencies(environmentId).getOrThrow().data

fun CurrencyService.createCurrency(currencyCode: CurrencyCode, environmentId: Long) =
    postCurrency(
        environmentId,
        CreateCurrencyRequestBody(
            currencyCode = currencyCode
        )
    ).getOrThrow().data.id

fun CommercialBankService.createCommercialBank(environmentId: Long, currencyId: Long) = createCommercialBank(
        environmentId,
        currencyId,
        CreateCommercialBankRequestBody("Test Bank")
    ).getOrThrow().data.id