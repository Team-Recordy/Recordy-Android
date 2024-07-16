package com.record.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.record.model.AuthEntity
import com.recordy.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel(){

    fun logout(){
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                authRepository.saveLocalData(AuthEntity("","",false))
            }.onFailure {
                //logout fail 재시작 로직
            }
        }
    }

    fun delete(){
        viewModelScope.launch {
            authRepository.delete().onSuccess {
                authRepository.saveLocalData(AuthEntity("","",false))
            }.onFailure {

            }
        }
    }

}
