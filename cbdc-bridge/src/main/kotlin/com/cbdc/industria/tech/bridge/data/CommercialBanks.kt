package com.cbdc.industria.tech.bridge.data

import com.cbdc.industria.tech.bridge.enums.PartyType
import com.cbdc.industria.tech.bridge.views.BankingEntityAccountView
import com.cbdc.industria.tech.bridge.views.CommercialBankView
import com.cbdc.industria.tech.bridge.views.PartyView
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CreateCommercialBankRequestBody(val commercialBankName: String)

@CordaSerializable
data class CreateCommercialBankResponseBody(val data: CreateCommercialBankResponseBodyData)

@CordaSerializable
data class CreateCommercialBankResponseBodyData(val id: Long)

@CordaSerializable
data class GetBankingEntityAccountResponseBody(val data: BankingEntityAccountView)

@CordaSerializable
data class GetCommercialBankDetailsResponseBody(val data: CommercialBankView)

@CordaSerializable
data class GetPartyResponseBody(val data: PartyView)

@CordaSerializable
data class OpenAccountRequestBody(val partyId: Long)

@CordaSerializable
data class RegisterPartyRequestBody(
    val partyType: PartyType,
    val partyFullLegalName: String,
    val partyPostalAddress: String
)

@CordaSerializable
data class RegisterPartyResponseBody(val data: RegisterPartyResponseBodyData)

@CordaSerializable
data class RegisterPartyResponseBodyData(val id: Long)

@CordaSerializable
data class GetPartyViewsPageResponseBody(
    val data: List<PartyView>,
    val first: Boolean,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
    val pageIndex: Int,
    val pageNumberOfElements: Int
)
