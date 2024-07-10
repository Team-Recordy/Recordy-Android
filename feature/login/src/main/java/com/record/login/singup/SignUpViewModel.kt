package com.record.login.singup

import com.record.model.ValidateResult
import com.record.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel<SignUpState, SignUpEffect>(SignUpState()) {

    fun allCheckEvent() {
        intent {
            val isChecked = !allChecked
            copy(
                allChecked = isChecked,
                serviceTermsChecked = isChecked,
                privacyPolicyChecked = isChecked,
                ageChecked = isChecked,
                btnEnable = isChecked,
            )
        }
    }

    fun checkServiceEvent() {
        intent {
            copy(serviceTermsChecked = !serviceTermsChecked)
        }
        allChecked()
    }

    fun checkPrivacyPolicyEvent() {
        intent {
            copy(privacyPolicyChecked = !privacyPolicyChecked)
        }
        allChecked()
    }

    fun checkAgeEvent() {
        intent {
            copy(ageChecked = !ageChecked)
        }
        allChecked()
    }

    fun navScreen(screenNumber: Int) {
        when (screenNumber) {
            0 -> {
                allChecked()
                intent {
                    copy(title = TITLE_POLICY_NAME)
                }
            }

            1 -> {
                checkValidateNickName()
                intent {
                    copy(title = TITLE_NAMING_NAME)
                } 
            }
            2 -> { // todo 화면이동
                intent {
                    copy(title = TITLE_SIGNUP_NAME)
                }
            }
        }
    }

    fun updateNickName(nickname: String) {
        intent {
            copy(nicknameText = nickname)
        }
        checkValidateNickName()
    }

    fun checkValidateNickName() =
        when {
            uiState.value.nicknameText.isBlank() -> {
                intent { copy(nicknameValidate = ValidateResult.Inputting, btnEnable = false) }
            }

            !nickNameDuplication(uiState.value.nicknameText) -> {
                intent { copy(nicknameValidate = ValidateResult.OverlapError, btnEnable = false) }
            }

            !nickNameRegex(uiState.value.nicknameText) -> {
                intent { copy(nicknameValidate = ValidateResult.ValidationError, btnEnable = false) }
            }

            else -> {
                intent { copy(nicknameValidate = ValidateResult.Success, btnEnable = true) }
            }
        }

    fun nickNameRegex(nickname: String): Boolean = Regex(NICKNAME_PATTERN).matches(nickname)
    fun nickNameDuplication(nickname: String): Boolean = true

    private fun allChecked() {
        intent {
            if (serviceTermsChecked && privacyPolicyChecked && ageChecked) {
                copy(allChecked = true, btnEnable = true)
            } else {
                copy(allChecked = false, btnEnable = false)
            }
        }
    }

    companion object {
        const val NICKNAME_PATTERN = "^[가-힣ㄱ-ㅎㅏ-ㅣ0-9_]+$"
        const val TITLE_POLICY_NAME = "이용약관"
        const val TITLE_NAMING_NAME = "닉네임 설명"
        const val TITLE_SIGNUP_NAME = "회원가입 완료"
    }
}
