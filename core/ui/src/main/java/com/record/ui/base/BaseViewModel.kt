package com.record.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<UI_STATE : UiState, SIDE_EFFECT : SideEffect>(
    initialState: UI_STATE,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect: Channel<SIDE_EFFECT> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    protected val currentState: UI_STATE
        get() = _uiState.value

    protected fun intent(reduce: UI_STATE.() -> UI_STATE) {
        val state = currentState.reduce()
        _uiState.update { state }
    }

    protected fun postSideEffect(vararg builder: SIDE_EFFECT) {
        for (effectValue in builder) {
            viewModelScope.launch { _sideEffect.send(effectValue) }
        }
    }
}
