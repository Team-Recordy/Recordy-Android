package com.record.login.singup

import androidx.lifecycle.viewModelScope
import com.record.model.ValidateResult
import com.record.ui.base.BaseViewModel
import com.recordy.auth.model.AuthAgreementEntity
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<SignUpState, SignUpEffect>(SignUpState()) {

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

            2 -> {
                intent {
                    copy(title = TITLE_SIGNUP_NAME, btnEnable = false)
                }
                signUp()
            }

            3 -> {}
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            authRepository.signUp(
                AuthAgreementEntity(
                    nickname = uiState.value.nicknameText,
                    termsAgreement = AuthAgreementEntity.TermsAgreement(
                        useTerm = true,
                        personalInfoTerm = true,
                        ageTerm = true,
                    ),
                ),
            ).onSuccess {
                intent {
                    copy(btnEnable = true)
                }
                authRepository.getLocalData().onSuccess {
                    authRepository.saveLocalData(it.copy(isSignedUp = true))
                }
            }.onFailure {
            }
        }
    }

    fun updateNickName(nickname: String) {
        intent {
            copy(nicknameText = nickname, btnEnable = false)
        }
    }

    private fun nickNameRegex(nickname: String): Boolean = NICKNAME_PATTERN.matches(nickname)

    fun checkValidateNickName() {
        viewModelScope.launch {
            authRepository.checkNickname(uiState.value.nicknameText).onSuccess {
                if (uiState.value.nicknameText.isBlank()) {
                    intent { copy(nicknameValidate = ValidateResult.Inputting, btnEnable = false) }
                } else if (!nickNameRegex(uiState.value.nicknameText) && !uiState.value.nicknameText.contains(" ")) {
                    intent { copy(nicknameValidate = ValidateResult.ValidationError, btnEnable = false) }
                } else {
                    intent { copy(nicknameValidate = ValidateResult.Success, btnEnable = true) }
                }
            }.onFailure {
                intent { copy(nicknameValidate = ValidateResult.OverlapError, btnEnable = false) }
            }
        }
    }

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
        val NICKNAME_PATTERN = Regex("^[가-힣ㅏ-ㅣ0-9_]+$")
        const val TITLE_POLICY_NAME = "이용약관"
        const val TITLE_NAMING_NAME = "닉네임 설명"
        const val TITLE_SIGNUP_NAME = "회원가입 완료"
    }
}
