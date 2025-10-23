package com.kevin.rickandmortyapp.app.data.remote


import com.kevin.rickandmortyapp.app.data.remote.model.Character
import com.kevin.rickandmortyapp.app.data.remote.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>

    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): Response<CharacterResponse>
}