package com.example.planify.profilePhotoAPI

import retrofit2.Call
import retrofit2.http.GET

interface ProfilePhotoApi {
    @GET("photos/random?query=portrait")
    fun getRandomProfilePhoto(): Call<ProfilePhoto>
}