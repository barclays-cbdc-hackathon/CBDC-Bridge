package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.data.MakeDepositRequestBody
import com.cbdc.industria.tech.bridge.services.PIPCordaService
import com.cbdc.industria.tech.bridge.services.PIPService
import com.cbdc.industria.tech.bridge.views.BankingEntityAccountView
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.serialization.CordaSerializable
import net.corda.core.utilities.getOrThrow

@StartableByRPC
class DepositFunds(
    private val envId: Long,
    private val currencyId: Long,
    private val pipId: Long,
    private val accountId: Long,
    private val amount: Long
): FlowLogic<BankingEntityAccountView>() {

    override fun call(): BankingEntityAccountView {
        val pipService = serviceHub.cordaService(PIPCordaService::class.java)
        return pipService.depositToAccount(
            xEnvId = envId,
            xCurrencyId = currencyId,
            pipId = pipId,
            accountId = accountId,
            body = MakeDepositRequestBody(amount)
        ).getOrThrow().data
    }
}

@CordaSerializable
data class DepositedFunds(
    val amount: Long
)