package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.services.PIPCordaService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.getOrThrow

@StartableByRPC
class DeleteAccountAndPartyPIP(
    private val accountAndPartyIds: PartyAndAccountIds,
    private val envId: Long,
    private val currencyId: Long,
    private val pipId: Long
) : FlowLogic<Unit>() {
    override fun call() {
        val pipService = serviceHub.cordaService(PIPCordaService::class.java)
        pipService.deletePIPAccount(envId, currencyId, pipId, accountAndPartyIds.accountId).getOrThrow()
        pipService.deletePIPParty(envId, currencyId, pipId, accountAndPartyIds.partyId).getOrThrow()
    }
}