package com.cbdc.industria.tech.webserver.rpc

import net.corda.core.messaging.CordaRPCOps

interface RPCOps {
    val proxy: CordaRPCOps
}
