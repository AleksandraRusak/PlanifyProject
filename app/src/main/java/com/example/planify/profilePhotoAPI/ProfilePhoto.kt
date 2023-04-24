package com.example.planify.profilePhotoAPI

// Data class representing a profile photo object returned by the Unsplash API
data class ProfilePhoto(
    val id: String,        // Unique identifier for the photo
    val urls: PhotoUrls    // Object containing different photo URLs
)

// Data class representing the photo URLs object within a profile photo
data class PhotoUrls(
    val small: String,     // URL of the photo with a small size
    val regular: String    // URL of the photo with a regular size
)