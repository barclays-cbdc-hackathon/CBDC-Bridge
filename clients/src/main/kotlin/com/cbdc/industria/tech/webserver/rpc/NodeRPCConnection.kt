package com.cbdc.industria.tech.webserver.rpc

import net.corda.client.rpc.CordaRPCClient
import net.corda.client.rpc.CordaRPCConnection
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy
import net.corda.client.rpc.GracefulReconnect
import net.corda.core.utilities.contextLogger

private const val CORDA_USER_NAME = "config.rpc.username"
private const val CORDA_USER_PASSWORD = "config.rpc.password"
private const val CORDA_NODE_HOST = "config.rpc.host"
private const val CORDA_RPC_PORT = "config.rpc.port"

/**
 * Wraps an RPC connection to a Corda node.
 *
 * The RPC connection is configured using command line arguments.
 *
 * @param host The host of the node we are connecting to.
 * @param rpcPort The RPC port of the node we are connecting to.
 * @param username The username for logging into the RPC client.
 * @param password The password for logging into the RPC client.
 * @property proxy The RPC proxy.
 */
@Component
open class NodeRPCConnection(
    @Value("\${$CORDA_NODE_HOST}") private val host: String,
    @Value("\${$CORDA_USER_NAME}") private val username: String,
    @Value("\${$CORDA_USER_PASSWORD}") private val password: String,
    @Value("\${$CORDA_RPC_PORT}") private val rpcPort: Int
) : RPCOps, AutoCloseable {

    private var rpcConnection: CordaRPCConnection? = null

    override val proxy: CordaRPCOps by lazy {
        val rpcAddress = NetworkHostAndPort(host, rpcPort)
        val gracefulReconnect = GracefulReconnect(
            onDisconnect = { logger.info("Node $rpcAddress disconnected!") },
            onReconnect = { logger.info("Node $rpcAddress is back again!") }
        )
        val connection = CordaRPCClient(rpcAddress).start(username, password, gracefulReconnect)
        rpcConnection = connection
        connection.proxy
    }

    @PreDestroy
    override fun close() {
        rpcConnection?.notifyServerAndClose()
    }

    companion object {
        val logger = contextLogger()
    }
}
