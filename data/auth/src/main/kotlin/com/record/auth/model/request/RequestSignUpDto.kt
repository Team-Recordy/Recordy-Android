package com.record.auth.model.request


import com.recordy.auth.model.AuthAgreementEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("termsAgreement")
    val termsAgreement: TermsAgreement,
) {
    @Serializable
    data class TermsAgreement(
        @SerialName("ageTerm")
        val ageTerm: Boolean,
        @SerialName("personalInfoTerm")
        val personalInfoTerm: Boolean,
        @SerialName("useTerm")
        val useTerm: Boolean,
    )
}

fun AuthAgreementEntity.toData(): RequestSignUpDto =
    RequestSignUpDto(
        nickname = nickname,
        termsAgreement = RequestSignUpDto.TermsAgreement(
            ageTerm = termsAgreement.ageTerm,
            personalInfoTerm = termsAgreement.personalInfoTerm,
            useTerm = termsAgreement.useTerm,
        ),
    )


