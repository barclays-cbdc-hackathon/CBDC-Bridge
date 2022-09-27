package com.cbdc.industria.tech.webserver

import com.cbdc.industria.tech.flows.CheckPings
import com.cbdc.industria.tech.webserver.rpc.NodeRPCConnection
import net.corda.core.messaging.startFlow
import net.corda.core.utilities.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @Autowired
    lateinit var node : NodeRPCConnection

    @GetMapping("/")
    fun hello(): String {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/ping")
    fun ping() : List<String>{
        return node.proxy.startFlow(::CheckPings).returnValue.getOrThrow()
    }
}