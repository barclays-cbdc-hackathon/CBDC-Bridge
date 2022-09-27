package com.cbdc.industria.tech.webserver.rpc

import com.cbdc.industria.tech.flows.CheckPings
import net.corda.core.messaging.startFlow
import net.corda.core.utilities.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @Autowired
    lateinit var node :NodeRPCConnection

    @GetMapping("/")
    fun hello(): String {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/ping")
    fun ping() : List<String>{
        return try {
            node.proxy.startFlow(::CheckPings).returnValue.getOrThrow()
//            ResponseEntity.status(HttpStatus.CREATED).body("Transaction id ${signedTx.id} committed to ledger.\n")

        } catch (ex: Throwable) {
            listOf<String>(ex.toString())
//            logger.error(ex.message, ex)
//            ResponseEntity.badRequest().body(ex.message!!)
        }
    }
}