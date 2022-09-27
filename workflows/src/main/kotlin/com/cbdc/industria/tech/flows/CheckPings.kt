package com.cbdc.industria.tech.flows

import co.paralleluniverse.fibers.Suspendable
import com.cbdc.industria.tech.bridge.services.StartCordaService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.getOrThrow

@StartableByRPC
class CheckPings : FlowLogic<List<String>>() {

    @Suspendable
    override fun call(): List<String> {
        val startService = serviceHub.cordaService(StartCordaService::class.java)

        val publicPing = try {
            startService.getPublicPing().getOrThrow().message
        } catch (e: Exception) {
            e.message!!
        }
        val authPing = try {
            startService.getAuthPing().getOrThrow().message
        } catch (e: Exception) {
            e.message!!
        }

        return listOf(publicPing, authPing)
    }
}
