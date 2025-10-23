package com.kevin.rickandmortyapp.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("origin")
    val origin: Origin   // 👈 AÑADIR ESTA LÍNEA
)

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
