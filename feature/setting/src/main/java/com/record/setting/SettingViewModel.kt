package com.record.setting

import android.util.Log
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

    fun Logout(){
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                Log.d("logout success", "Logout: $it")
                authRepository.saveLocalData(AuthEntity("","",false))
            }.onFailure {
                Log.d("logout fail", "Logout: $it")
                //logout fail
            }
        }

    }

}
