package com.cbdc.industria.tech.bridge.views

import com.cbdc.industria.tech.bridge.enums.CurrencyStatus
import com.cbdc.industria.tech.bridge.models.CBDCAccountChild
import com.cbdc.industria.tech.bridge.models.CommercialBankChild
import com.cbdc.industria.tech.bridge.models.Currency
import com.cbdc.industria.tech.bridge.models.PIPChild
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CurrencyView(
    val id: Long,
    val status: CurrencyStatus,
    val currencyDetails: Currency,
    val cbdcAccounts: Set<CBDCAccountChild>,
    val commercialBanks: Set<CommercialBankChild>,
    val pips: Set<PIPChild>
)
