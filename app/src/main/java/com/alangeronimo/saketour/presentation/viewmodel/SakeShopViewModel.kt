package com.alangeronimo.saketour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alangeronimo.domain.model.SakeShop
import com.alangeronimo.domain.useCase.GetSakeShopsUseCase
import com.alangeronimo.saketour.presentation.state.SakeUiEvent
import com.alangeronimo.saketour.presentation.state.SakeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SakeShopViewModel(
    private val getSakeShopsUseCase: GetSakeShopsUseCase,
) : ViewModel(), ISakeShopViewModel {

    private val _state = MutableStateFlow(SakeUiState())
    override val state: StateFlow<SakeUiState> = _state.asStateFlow()

    fun onEvent(event: SakeUiEvent) = when (event) {
        is SakeUiEvent.LoadShops -> handleLoadSakeShops()
        is SakeUiEvent.LoadSuccess -> handleLoadSuccess(event.shops)
        is SakeUiEvent.LoadFailed -> handleErrorMessage(event.errorMessage)
    }

    override fun findShopByName(
        name: String,
    ): SakeShop? = state.value.sakeShops.find { it.name == name }

    private fun handleLoadSakeShops() {
        if (!state.value.sakeShops.isEmpty()) return
        loadSakeShops()
    }

    private fun loadSakeShops() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        try {
            val shops: List<SakeShop> = getSakeShopsUseCase()
            onEvent(SakeUiEvent.LoadSuccess(shops))
        } catch (e: Exception) {
            onEvent(
                event = SakeUiEvent.LoadFailed("Failed to load: ${e.message}"),
            )
        }
    }

    private fun handleLoadSuccess(shops: List<SakeShop>) {
        _state.value = _state.value.copy(
            isLoading = false,
            error = null,
            sakeShops = shops,
        )
    }

    private fun handleErrorMessage(message: String) {
        _state.value = _state.value.copy(
            error = message,
        )
    }
}