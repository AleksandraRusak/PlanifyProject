package com.example.planify.profilePhotoAPI

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Base URL for Unsplash API
    private const val BASE_URL = "https://api.unsplash.com/"

    // OkHttpClient with an interceptor to add the required authorization header
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            // Building a new request with the Authorization header
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization",
                    "Client-ID 4o0hlxLcIzUYXuYXB56-4YKH_Go_MVYMQSfs5rggDeE")
            // Proceed with the new request containing the Authorization header
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    // Creating a Retrofit instance with the base URL and OkHttpClient
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    // Creating an API instance from the Retrofit instance for the ProfilePhotoApi interface
    val api: ProfilePhotoApi = retrofit.create(ProfilePhotoApi::class.java)
}