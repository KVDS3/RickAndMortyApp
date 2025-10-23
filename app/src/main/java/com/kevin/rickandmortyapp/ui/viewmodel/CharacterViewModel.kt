package com.kevin.rickandmortyapp.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.rickandmortyapp.app.data.repository.CharacterRepository
import com.kevin.rickandmortyapp.app.data.repository.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kevin.rickandmortyapp.app.data.remote.model.Character
import com.kevin.rickandmortyapp.app.data.remote.model.CharacterResponse
class CharacterViewModel : ViewModel() {
    private val repository = CharacterRepository()

    private val _characters = MutableStateFlow<NetworkResult<CharacterResponse>>(NetworkResult.Loading())
    val characters: StateFlow<NetworkResult<CharacterResponse>> = _characters.asStateFlow()

    private val _selectedCharacter = MutableStateFlow<NetworkResult<Character>>(NetworkResult.Loading())
    val selectedCharacter: StateFlow<NetworkResult<Character>> = _selectedCharacter.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            repository.getCharacters().collect { result ->
                _characters.value = result
            }
        }
    }

    fun loadCharacterById(id: Int) {
        viewModelScope.launch {
            repository.getCharacterById(id).collect { result ->
                _selectedCharacter.value = result
            }
        }
    }

    fun refresh() {
        loadCharacters()
    }
}