package com.alangeronimo.data.dto

import com.google.gson.annotations.SerializedName

data class SakeShopDto(
    val name: String,
    val description: String,
    val picture: String?,
    val rating: Float,
    val address: String,
    @SerializedName("google_maps_link")
    val googleMapsLink: String,
    val website: String,
)
