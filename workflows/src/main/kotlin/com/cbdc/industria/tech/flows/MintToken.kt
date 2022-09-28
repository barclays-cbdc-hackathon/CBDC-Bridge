package com.cbdc.industria.tech.flows

import co.paralleluniverse.fibers.Suspendable
import com.cbdc.industria.tech.contracts.FungibleHouseTokenState
import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.r3.corda.lib.tokens.contracts.types.TokenPointer
import com.r3.corda.lib.tokens.contracts.types.TokenType
import com.r3.corda.lib.tokens.contracts.utilities.issuedBy
import com.r3.corda.lib.tokens.contracts.utilities.withNotary
import com.r3.corda.lib.tokens.workflows.flows.rpc.CreateEvolvableTokens
import com.r3.corda.lib.tokens.workflows.flows.rpc.IssueTokens
import com.r3.corda.lib.tokens.workflows.flows.rpc.MoveFungibleTokens
import com.r3.corda.lib.tokens.workflows.flows.rpc.MoveFungibleTokensHandler
import com.r3.corda.lib.tokens.workflows.utilities.tokenBalance
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.*
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.utilities.ProgressTracker


@StartableByRPC
class MintToken: FlowLogic<Int>(){

    @Suspendable
    override fun call(): Int {
        TODO()
    }
}


@StartableByRPC
class CreateHouseTokenFlow(val symbol: String,
                           val valuationOfHouse:Int) : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call():String {
        // Obtain a reference from a notary we wish to use.
        val notary = serviceHub.networkMapCache.getNotary(CordaX500Name.parse("O=Notary,L=London,C=GB"))

        //create token type
        val evolvableTokenTypeHouseState = FungibleHouseTokenState(valuationOfHouse,ourIdentity,
            UniqueIdentifier(),0,symbol)

        //warp it with transaction state specifying the notary
        val transactionState = evolvableTokenTypeHouseState withNotary notary!!

        //call built in sub flow CreateEvolvableTokens. This can be called via rpc or in unit testing
        val stx = subFlow(CreateEvolvableTokens(transactionState))

        return "Fungible house token $symbol has created with valuationL: $valuationOfHouse " +
                "\ntxId: ${stx.id}"
    }
}


@StartableByRPC
class IssueHouseTokenFlow(val symbol: String,
                          val quantity: Long,
                          val holder: Party
) : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call():String {

        //get house states on ledger with uuid as input tokenId
        val stateAndRef = serviceHub.vaultService.queryBy(FungibleHouseTokenState::class.java)
            .states.filter { it.state.data.symbol == symbol }[0]

        //get the RealEstateEvolvableTokenType object
        val evolvableTokenType = stateAndRef.state.data

        //get the pointer pointer to the house
        val tokenPointer: TokenPointer<*> = evolvableTokenType.toPointer(evolvableTokenType.javaClass)

        //assign the issuer to the house type who will be issuing the tokens
        val issuedTokenType = tokenPointer issuedBy ourIdentity

        //specify how much amount to issue to holder
        val amount = Amount(quantity,issuedTokenType)

        val fungibletoken = FungibleToken(amount,holder)

        val stx = subFlow(IssueTokens(listOf(fungibletoken)))
        return "Issued $quantity $symbol token to ${holder.name.organisation}"
    }
}


@InitiatingFlow
@StartableByRPC
class GetTokenBalance(val symbol:String) : FlowLogic<Long>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call():Long {
        //get house states on ledger with uuid as input tokenId
        val stateAndRef = serviceHub.vaultService.queryBy(FungibleHouseTokenState::class.java)
            .states.filter { it.state.data.symbol == symbol }[0]

        //get the Token State object
        val evolvableTokenType = stateAndRef.state.data
        //get the pointer pointer to the house
        val tokenPointer = evolvableTokenType.toPointer(evolvableTokenType.javaClass)

        //retrieve amount
        val amount: Amount<TokenType> = serviceHub.vaultService.tokenBalance(tokenPointer)

        //return "\n You currently have " + amount.quantity + " " + symbol + " Tokens issued by "+evolvableTokenType.maintainer.name.organisation+"\n";
        return amount.quantity
    }
}


@InitiatingFlow
@StartableByRPC
class MoveHouseTokenFlow(val symbol: String,
                         val holder: Party,
                         val quantity: Long) : FlowLogic<String>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call():String {
        //get house states on ledger with uuid as input tokenId
        val stateAndRef = serviceHub.vaultService.queryBy(FungibleHouseTokenState::class.java)
            .states.filter { it.state.data.symbol == symbol }[0]

        //get the RealEstateEvolvableTokenType object
        val evolvableTokenType = stateAndRef.state.data

        //get the pointer pointer to the house
        val tokenPointer: TokenPointer<FungibleHouseTokenState> = evolvableTokenType.toPointer(evolvableTokenType.javaClass)

        //specify how much amount to issue to holder
        val amount:Amount<TokenType> = Amount(quantity,tokenPointer)
        val stx = subFlow(MoveFungibleTokens(amount,holder))

        return "Moved $quantity $symbol token(s) to ${holder.name.organisation}"+
                "\ntxId: ${stx.id}"
    }
}

@InitiatedBy(MoveHouseTokenFlow::class)
class MoveHouseTokenFlowResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        // Simply use the MoveFungibleTokensHandler as the responding flow
        return subFlow(MoveFungibleTokensHandler(counterpartySession))

    }
}