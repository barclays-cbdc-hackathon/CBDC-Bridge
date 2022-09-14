package com.cbdc.industria.tech.contracts

import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class BarclaysCBDCHackathonContract : Contract {

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
    }

    private inline fun <reified T : ContractState> LedgerTransaction.requireSingleOutput() =
        outputsOfType<T>().singleOrNull() ?: error("A single output of type ${T::class.java.simpleName} is required")

    private inline fun <reified T : ContractState> LedgerTransaction.requireSingleInput() =
        inputsOfType<T>().singleOrNull() ?: error("A single input of type ${T::class.java.simpleName} is required")

    interface Commands : CommandData

    companion object {
        val ID: String = this::class.java.enclosingClass.canonicalName
    }
}
