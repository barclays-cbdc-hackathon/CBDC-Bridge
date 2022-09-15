package com.cbdc.industria.tech.bridge.services

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MockStartService {

    private val executor = Executors.newFixedThreadPool(THREADS_COUNT)

    fun getPublicPing(): Future<String> {
        val future = CompletableFuture<String>()

        executor.execute {
            val result = makeGetRequest<String>(url = "$HOST_URL/start-here/public-ping")
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAuthPing(authToken: String): Future<String> {
        val future = CompletableFuture<String>()

        executor.execute {
            val result = makeGetRequest<String>(
                url = "$HOST_URL/start-here/public-ping",
                headers = mapOf(AUTH_HEADER_KEY to authToken)
            )
            result.toCompletableFuture(future)
        }

        return future
    }
}