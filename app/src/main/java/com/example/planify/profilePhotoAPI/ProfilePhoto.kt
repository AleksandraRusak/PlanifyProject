package com.example.planify.profilePhotoAPI

data class ProfilePhoto(
    val id: String,
    val urls: PhotoUrls
)

data class PhotoUrls(
    val small: String,
    val regular: String
)