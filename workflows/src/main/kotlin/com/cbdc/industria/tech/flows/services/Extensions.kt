package com.cbdc.industria.tech.flows.services

import net.corda.core.node.ServiceHub
import net.corda.core.transactions.TransactionBuilder

fun ServiceHub.notary() = networkMapCache.notaryIdentities.first()

fun ServiceHub.txBuilder() = TransactionBuilder(notary())
