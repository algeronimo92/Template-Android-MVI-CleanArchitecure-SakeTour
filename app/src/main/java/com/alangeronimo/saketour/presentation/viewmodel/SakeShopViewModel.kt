package com.alangeronimo.saketour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alangeronimo.domain.model.SakeShop
import com.alangeronimo.domain.useCase.GetSakeShopsUseCase
import com.alangeronimo.saketour.presentation.state.SakeUiEvent
import com.alangeronimo.saketour.presentation.state.SakeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SakeShopViewModel(
    private val getSakeShopsUseCase: GetSakeShopsUseCase,
) : ViewModel(), ISakeShopViewModel {

    private val _state = MutableStateFlow(SakeUiState())
    override val state: StateFlow<SakeUiState> = _state

    init {
        loadSakeShops()
    }

    override fun findShopByName(name: String): SakeShop? {
        return state.value.sakeShops.find { it.name == name }
    }

    fun processEvent(event: SakeUiEvent) {
        _state.value = reduce(_state.value, event)
    }

    fun loadSakeShops() = viewModelScope.launch {
        processEvent(SakeUiEvent.LoadShops)
        _state.value = _state.value.copy(isLoading = true)
        try {
            val shops = getSakeShopsUseCase()
            processEvent(SakeUiEvent.LoadSuccess(shops))
        } catch (e: Exception) {
            processEvent(
                event = SakeUiEvent.LoadFailed("Failed to load: ${e.message}"),
            )
        }
    }

    private fun reduce(
        current: SakeUiState,
        event: SakeUiEvent,
    ): SakeUiState = when (event) {
        is SakeUiEvent.LoadShops -> current.copy(isLoading = true, error = null)
        is SakeUiEvent.LoadSuccess -> SakeUiState(sakeShops = event.shops)
        is SakeUiEvent.LoadFailed -> SakeUiState(error = event.errorMessage)
    }
}
