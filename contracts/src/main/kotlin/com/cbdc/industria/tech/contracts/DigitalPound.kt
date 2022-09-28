package com.cbdc.industria.tech.contracts

import com.r3.corda.lib.tokens.contracts.EvolvableTokenContract
import com.r3.corda.lib.tokens.contracts.FungibleTokenContract
import com.r3.corda.lib.tokens.contracts.states.EvolvableTokenType
import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType
import net.corda.core.contracts.Amount
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.Contract
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.transactions.LedgerTransaction

@BelongsToContract(DigitalPoundStateContract::class)
data class DigitalPoundState(
    override val amount: Amount<IssuedTokenType>,
    override val holder: AbstractParty
):FungibleToken(amount, holder)

class DigitalPoundStateContract : FungibleTokenContract(), Contract{
    companion object{
        const val CONTACT_ID = "com.cbdc.industria.tech.contracts.DigitalPoundStateContract"
    }
}


class HouseTokenStateContract : EvolvableTokenContract(), Contract {
    companion object {
        const val CONTRACT_ID = "net.corda.samples.tokenizedhouse.contracts.HouseTokenStateContract"
    }
    override fun additionalCreateChecks(tx: LedgerTransaction) {
        // Write contract validation logic to be performed while creation of token
        val outputState = tx.getOutput(0) as FungibleHouseTokenState
        outputState.apply {
            require(outputState.valuation > 0) {"Valuation must be greater than zero"}
        }
    }

    override fun additionalUpdateChecks(tx: LedgerTransaction) {
        // Write contract validation logic to be performed while updation of token
    }

}

@BelongsToContract(HouseTokenStateContract::class)
data class FungibleHouseTokenState(val valuation: Int,
                                   val maintainer: Party,
                                   override val linearId: UniqueIdentifier,
                                   override val fractionDigits: Int,
                                   val symbol:String,
                                   override val maintainers: List<Party> = listOf(maintainer)
) : EvolvableTokenType()