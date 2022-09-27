package com.cbdc.industria.tech.bridge.services

import com.cbdc.industria.tech.bridge.data.CreateCommercialBankRequestBody
import com.cbdc.industria.tech.bridge.data.CreateCommercialBankResponseBody
import com.cbdc.industria.tech.bridge.data.GetBankingEntityAccountResponseBody
import com.cbdc.industria.tech.bridge.data.GetBankingEntityAccountsPageResponseBody
import com.cbdc.industria.tech.bridge.data.GetCommercialBankDetailsPageResponseBody
import com.cbdc.industria.tech.bridge.data.GetCommercialBankDetailsResponseBody
import com.cbdc.industria.tech.bridge.data.GetPartyResponseBody
import com.cbdc.industria.tech.bridge.data.GetPartyViewsPageResponseBody
import com.cbdc.industria.tech.bridge.data.MakeDepositRequestBody
import com.cbdc.industria.tech.bridge.data.MakeWithdrawalRequestBody
import com.cbdc.industria.tech.bridge.data.OpenAccountRequestBody
import com.cbdc.industria.tech.bridge.data.OpenAccountResponseBody
import com.cbdc.industria.tech.bridge.data.RegisterPartyRequestBody
import com.cbdc.industria.tech.bridge.data.RegisterPartyResponseBody
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import net.corda.core.node.AppServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.serialization.SingletonSerializeAsToken

@CordaService
class CommercialBankCordaService(private val serviceHub: AppServiceHub) : CommercialBankService(
    executor = Executors.newFixedThreadPool(THREADS_COUNT),
    host = HOST_URL
)


open class CommercialBankService(
    private val executor: ExecutorService,
    private val host: String
) : SingletonSerializeAsToken() {

    fun createCommercialBank(
        xEnvId: Long,
        xCurrencyId: Long,
        body: CreateCommercialBankRequestBody
    ): Future<CreateCommercialBankResponseBody> {
        val future = CompletableFuture<CreateCommercialBankResponseBody>()

        executor.execute {
            val result = makePostRequest<CreateCommercialBankResponseBody>(
                url = "$host/$COMMERCIAL_BANKS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getCommercialBank(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long
    ): Future<GetCommercialBankDetailsResponseBody> {
        val future = CompletableFuture<GetCommercialBankDetailsResponseBody>()
        executor.execute {
            val result = makeGetRequest<GetCommercialBankDetailsResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getCommercialBanks(
        xEnvId: Long,
        xCurrencyId: Long,
        pageIndex: Int = 0,
        pageSize: Int = 20
    ): Future<GetCommercialBankDetailsPageResponseBody> {
        if (pageSize > MAX_PAGE_SIZE)
            throw IllegalArgumentException("pageSize must not be grater than $MAX_PAGE_SIZE.")

        val future = CompletableFuture<GetCommercialBankDetailsPageResponseBody>()

        executor.execute {
            val result = makeGetRequest<GetCommercialBankDetailsPageResponseBody>(
                url = "$host/$COMMERCIAL_BANKS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                ),
                params = listOf(Pair(PAGE_INDEX_KEY, pageIndex), Pair(PAGE_SIZE_KEY, pageSize))
            )
            result.toCompletableFuture(future)
        }
        return future
    }

    fun deleteCommercialBank(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long
    ): Future<GetCommercialBankDetailsResponseBody> {
        val future = CompletableFuture<GetCommercialBankDetailsResponseBody>()
        executor.execute {
            val result = makeDeleteRequest<GetCommercialBankDetailsResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun registerPartyWithBank(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        body: RegisterPartyRequestBody
    ): Future<RegisterPartyResponseBody> {
        val future = CompletableFuture<RegisterPartyResponseBody>()
        executor.execute {
            val result = makePostRequest<RegisterPartyResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_PARTIES",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getParty(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        partyId: Long
    ): Future<GetPartyResponseBody> {
        val future = CompletableFuture<GetPartyResponseBody>()
        executor.execute {
            val result = makeGetRequest<GetPartyResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_PARTIES/$partyId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getParties(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        pageIndex: Int = 0,
        pageSize: Int = 20
    ): Future<GetPartyViewsPageResponseBody> {
        if (pageSize > MAX_PAGE_SIZE)
            throw IllegalArgumentException("pageSize must not be grater than $MAX_PAGE_SIZE.")

        val future = CompletableFuture<GetPartyViewsPageResponseBody>()

        executor.execute {
            val result = makeGetRequest<GetPartyViewsPageResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_PARTIES",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                ),
                params = listOf(Pair(PAGE_INDEX_KEY, pageIndex), Pair(PAGE_SIZE_KEY, pageSize))
            )
            result.toCompletableFuture(future)
        }
        return future
    }

    fun deleteParty(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        partyId: Long
    ): Future<GetPartyResponseBody> {
        val future = CompletableFuture<GetPartyResponseBody>()
        executor.execute {
            val result = makeDeleteRequest<GetPartyResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_PARTIES/$partyId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    /**
     * Open an account at the commercial bank.
     * You need to provide a reference to the ID of the party which is opening the account in the request body.
     */
    fun openAccount(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        body: OpenAccountRequestBody
    ): Future<OpenAccountResponseBody> {
        val future = CompletableFuture<OpenAccountResponseBody>()
        executor.execute {
            val result = makePostRequest<OpenAccountResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAccount(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        accountId: Long
    ): Future<GetBankingEntityAccountResponseBody> {
        val future = CompletableFuture<GetBankingEntityAccountResponseBody>()
        executor.execute {
            val result = makeGetRequest<GetBankingEntityAccountResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS/$accountId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAccounts(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        pageIndex: Int = 0,
        pageSize: Int = 20
    ): Future<GetBankingEntityAccountsPageResponseBody> {
        if (pageSize > MAX_PAGE_SIZE)
            throw IllegalArgumentException("pageSize must not be grater than $MAX_PAGE_SIZE.")

        val future = CompletableFuture<GetBankingEntityAccountsPageResponseBody>()

        executor.execute {
            val result = makeGetRequest<GetBankingEntityAccountsPageResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                ),
                params = listOf(Pair(PAGE_INDEX_KEY, pageIndex), Pair(PAGE_SIZE_KEY, pageSize))
            )
            result.toCompletableFuture(future)
        }
        return future
    }

    fun deleteAccount(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        accountId: Long
    ): Future<GetBankingEntityAccountResponseBody> {
        val future = CompletableFuture<GetBankingEntityAccountResponseBody>()
        executor.execute {
            val result = makeDeleteRequest<GetBankingEntityAccountResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS/$accountId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId
                )
            )
            result.toCompletableFuture(future)
        }
        return future
    }

    fun deposit(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        accountId: Long,
        body: MakeDepositRequestBody
    ): Future<GetBankingEntityAccountResponseBody> {
        val future = CompletableFuture<GetBankingEntityAccountResponseBody>()
        executor.execute {
            val result = makePostRequest<GetBankingEntityAccountResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS/$accountId/$COMMERCIAL_BANKS_ACCOUNTS_DEPOSIT",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun withdrawalAccount(
        xEnvId: Long,
        xCurrencyId: Long,
        bankId: Long,
        accountId: Long,
        body: MakeWithdrawalRequestBody
    ): Future<GetBankingEntityAccountResponseBody> {
        val future = CompletableFuture<GetBankingEntityAccountResponseBody>()
        executor.execute {
            val result = makePostRequest<GetBankingEntityAccountResponseBody>(
                url = "$host/$COMMERCIAL_BANKS/$bankId/$COMMERCIAL_BANKS_ACCOUNTS/$accountId/$COMMERCIAL_BANKS_ACCOUNTS_WITHDRAWAL",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to xCurrencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }
}
