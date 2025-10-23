package com.kevin.rickandmortyapp.app.data.remote


object ApiClient {
    val apiService: RickAndMortyApiService by lazy {
        RetrofitClient.createService(RickAndMortyApiService::class.java)
    }
}