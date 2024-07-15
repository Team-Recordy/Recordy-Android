package com.recordy.auth.model

data class AuthAgreementEntity(
    val nickname: String,
    val termsAgreement: TermsAgreement,
) {
    data class TermsAgreement(
        val useTerm: Boolean,
        val personalInfoTerm: Boolean,
        val ageTerm: Boolean,
    )
}
