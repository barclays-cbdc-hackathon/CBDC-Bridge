package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.views.CBDCAccountView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class GetCBDCAccountResponseBody(val data: CBDCAccountView)

@CordaSerializable
data class MakeDepositRequestBody(val depositAmountInCurrencyUnits: Long)

@CordaSerializable
data class MakeWithdrawalRequestBody(val withdrawalAmountInCurrencyUnits: Long)

@CordaSerializable
data class OpenAccountResponseBody(val data: OpenAccountResponseBodyData)

@CordaSerializable
data class OpenAccountResponseBodyData(val id: Long)
