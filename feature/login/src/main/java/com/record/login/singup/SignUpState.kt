package com.record.login.singup

import com.record.model.ValidateResult
import com.record.ui.base.SideEffect
import com.record.ui.base.UiState

data class SignUpState(
    val allChecked: Boolean = false,
    val serviceTermsChecked: Boolean = false,
    val privacyPolicyChecked: Boolean = false,
    val ageChecked: Boolean = false,

    val btnEnable: Boolean = false,

    val nicknameText: String = "",
    val nicknameValidate: ValidateResult = ValidateResult.Inputting,
    val labelText: String = "",

    val title: String = "이용약관",
) : UiState

sealed interface SignUpEffect : SideEffect {
    data object NavigateToHome : SignUpEffect
}
