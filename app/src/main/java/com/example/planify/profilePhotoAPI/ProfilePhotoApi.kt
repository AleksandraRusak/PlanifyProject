package com.example.planify.profilePhotoAPI

import retrofit2.Call
import retrofit2.http.GET

// Interface to define the Unsplash API endpoints
interface ProfilePhotoApi {
    // Endpoint to fetch a random profile photo with a "portrait" query
    @GET("photos/random?query=portrait")
    //The getRandomProfilePhoto() function returns a Call<ProfilePhoto> object
    // that can be used to make the API request and handle the response.
    fun getRandomProfilePhoto(): Call<ProfilePhoto>
}