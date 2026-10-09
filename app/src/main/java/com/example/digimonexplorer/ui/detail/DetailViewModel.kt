package com.example.digimonexplorer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digimonexplorer.data.model.Digimon
import com.example.digimonexplorer.data.repository.DigimonRepository
import com.example.digimonexplorer.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DigimonRepository = DigimonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Digimon>>(UiState.Loading)
    val uiState: StateFlow<UiState<Digimon>> = _uiState.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getDigimonDetail(id))
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}
