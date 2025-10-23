package com.kevin.rickandmortyapp.app.data.repository


import com.kevin.rickandmortyapp.app.data.remote.ApiClient
import com.kevin.rickandmortyapp.app.data.remote.model.Character
import com.kevin.rickandmortyapp.app.data.remote.model.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository {
    private val apiService = ApiClient.apiService

    fun getCharacters(): Flow<NetworkResult<CharacterResponse>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = apiService.getCharacters()
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Error desconocido"))
        }
    }.flowOn(Dispatchers.IO)

    fun getCharacterById(id: Int): Flow<NetworkResult<Character>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = apiService.getCharacterById(id)
            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResult.Success(response.body()!!))
            } else {
                emit(NetworkResult.Error("Personaje no encontrado"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Error al cargar personaje"))
        }
    }.flowOn(Dispatchers.IO)
}