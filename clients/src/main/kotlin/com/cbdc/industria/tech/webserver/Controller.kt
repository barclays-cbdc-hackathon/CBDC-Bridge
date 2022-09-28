package com.cbdc.industria.tech.webserver

import com.cbdc.industria.tech.flows.*
import com.cbdc.industria.tech.webserver.rpc.NodeRPCConnection
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.startFlow
import net.corda.core.utilities.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    val SERVICE_NAMES = listOf("Notary", "Network Map Service")

    @Autowired
    lateinit var node : NodeRPCConnection

    @GetMapping("/")
    fun hello(): String {
        return "Greetings from Spring Boot!";
    }

    /**
     * Returns the node's name.
     */
    @GetMapping("/me")
    fun whoami() = mapOf("me" to node.proxy.nodeInfo().legalIdentities.first().name)

    /**
     * Returns all parties registered with the network map service. These names can be used to look up identities using
     * the identity service.
     */
    @GetMapping("/peers")
    fun getPeers(): Map<String, List<CordaX500Name>> {
        val nodeInfo = node.proxy.networkMapSnapshot()
        return mapOf("peers" to nodeInfo
            .map { it.legalIdentities.first().name }
            //filter out myself, notary and eventual network map started by driver
            .filter { it.organisation !in (SERVICE_NAMES + node.proxy.nodeInfo().legalIdentities.first().name.organisation) })
    }

    @GetMapping("/ping")
    fun ping() : List<String>{
        return node.proxy.startFlow(::CheckPings).returnValue.getOrThrow()
    }

    @GetMapping("/create")
    fun createEGBP():String{
        return node.proxy.startFlow(::CreateHouseTokenFlow, "EGBP", 1).returnValue.getOrThrow()
    }

    @GetMapping("/issue")
    fun issueEGBP():String{
        val party = node.proxy.nodeInfo().legalIdentities.first()
        return node.proxy.startFlow(::IssueHouseTokenFlow, "EGBP", 100, party).returnValue.getOrThrow()
    }

    @GetMapping("/balance")
    fun balance(): Long{
        return node.proxy.startFlow(::GetTokenBalance, "EGBP").returnValue.getOrThrow()
    }

    @GetMapping("/transfer")
    fun transferCBOEtoPartyB(
        @RequestParam("bankId") bankId: Long = 1709,
        @RequestParam("accountId") accountId: Long = 1920,
        @RequestParam("qty") qty: Long = 100
        ): String{
        println("""
            making a request to ledger with folowwing parameters:
            bankId : ${bankId}
            accountID: ${accountId}
            qty: ${qty}
        """.trimIndent())
        val partyName = "O=PartyB,L=New York,C=US"
        val partyX500Name = CordaX500Name.parse(partyName)
        val holder = node.proxy.wellKnownPartyFromX500Name(partyX500Name) ?: return "Party named $partyName cannot be found."
        return node.proxy.startFlow(::MoveHouseTokenFlow, "EGBP", holder, qty, bankId, accountId ).returnValue.getOrThrow()
    }
}