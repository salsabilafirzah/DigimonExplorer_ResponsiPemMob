package com.example.digimonexplorer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digimonexplorer.data.model.Digimon
import com.example.digimonexplorer.data.repository.DigimonRepository
import com.example.digimonexplorer.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DigimonRepository = DigimonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Digimon>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Digimon>>> = _uiState.asStateFlow()

    init { loadDigimon() }

    fun loadDigimon() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getDigimonList())
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}
