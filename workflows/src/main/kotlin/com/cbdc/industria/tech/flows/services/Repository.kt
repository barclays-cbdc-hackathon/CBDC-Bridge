package com.cbdc.industria.tech.flows.services

import kotlin.reflect.KProperty1
import net.corda.core.contracts.StateAndRef
import net.corda.core.node.AppServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.builder
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import net.corda.core.serialization.SingletonSerializeAsToken

@CordaService
class Repository(val serviceHub: AppServiceHub) : SingletonSerializeAsToken() {

    inline fun <reified S : QueryableState, T : PersistentState, I : Any> customQuerySingle(
        column: KProperty1<T, I?>,
        id: I
    ): StateAndRef<S>? {
        val expression = builder { column.equal(id) }
        val criteria = QueryCriteria.VaultCustomQueryCriteria(expression = expression)
        return serviceHub.vaultService.queryBy<S>(criteria).states.singleOrNull()
    }

    inline fun <reified S : QueryableState, T : PersistentState, I : Comparable<I>> customQueryList(
        column: KProperty1<T, I?>,
        emails: Collection<I>
    ): List<StateAndRef<S>> {
        val expression = builder { column.`in`(emails) }
        val criteria = QueryCriteria.VaultCustomQueryCriteria(expression = expression)
        return serviceHub.vaultService.queryBy<S>(criteria).states
    }
}
