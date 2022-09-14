package com.cbdc.industria.tech.bridge.services

import com.cbdc.industria.tech.bridge.data.GetCBDCAccountResponseBody
import com.cbdc.industria.tech.bridge.data.MakeDepositRequestBody
import com.cbdc.industria.tech.bridge.data.MakeWithdrawalRequestBody
import com.cbdc.industria.tech.bridge.data.OpenAccountResponseBody
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import net.corda.core.node.AppServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.serialization.SingletonSerializeAsToken

@CordaService
class CBDCLedgerCordaService(val serviceHub: AppServiceHub) : CBDCLedgerService(
    executor = Executors.newFixedThreadPool(THREADS_COUNT),
    host = HOST_URL
)

open class CBDCLedgerService(
    private val executor: ExecutorService,
    private val host: String
) : SingletonSerializeAsToken() {

    fun openAccount(
        xEnvId: Int,
        xCurrencyId: Int,
        xPipId: Int
    ): Future<OpenAccountResponseBody> {
        val future = CompletableFuture<OpenAccountResponseBody>()

        executor.execute {
            val result = makePostRequest<OpenAccountResponseBody>(
                url = "$host/$CBDC_ACCOUNTS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    X_PIP_ID to xPipId,
                    CONTENT_TYPE to APPLICATION_JSON
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun withdrawal(
        xEnvId: Int,
        xCurrencyId: Int,
        xPipId: Int,
        accountId: Int,
        body: MakeWithdrawalRequestBody
    ): Future<GetCBDCAccountResponseBody> {
        val future = CompletableFuture<GetCBDCAccountResponseBody>()

        executor.execute {
            val result = makePostRequest<GetCBDCAccountResponseBody>(
                url = "$host/$CBDC_ACCOUNTS/$accountId/$CBDC_ACCOUNTS_WITHDRAWAL",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    X_PIP_ID to xPipId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun deposit(
        xEnvId: Int,
        xCurrencyId: Int,
        xPipId: Int,
        accountId: Int,
        body: MakeDepositRequestBody
    ): Future<GetCBDCAccountResponseBody> {
        val future = CompletableFuture<GetCBDCAccountResponseBody>()

        executor.execute {
            val result = makePostRequest<GetCBDCAccountResponseBody>(
                url = "$host/$CBDC_ACCOUNTS/$accountId/$CBDC_ACCOUNTS_DEPOSIT",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    X_PIP_ID to xPipId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAccount(
        xEnvId: Int,
        xCurrencyId: Int,
        xPipId: Int,
        accountId: Int
    ): Future<GetCBDCAccountResponseBody> {
        val future = CompletableFuture<GetCBDCAccountResponseBody>()

        executor.execute {
            val result = makeGetRequest<GetCBDCAccountResponseBody>(
                url = "$host/$CBDC_ACCOUNTS/$accountId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    X_PIP_ID to xPipId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }


    fun deleteAccount(
        xEnvId: Int,
        xCurrencyId: Int,
        xPipId: Int,
        accountId: Int
    ): Future<GetCBDCAccountResponseBody> {
        val future = CompletableFuture<GetCBDCAccountResponseBody>()

        executor.execute {
            val result = makeDeleteRequest<GetCBDCAccountResponseBody>(
                url = "$host/$CBDC_ACCOUNTS/$accountId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    X_PIP_ID to xPipId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }


}
