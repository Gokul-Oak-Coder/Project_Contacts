package com.example.projectcontactinfo.ui.network

import com.example.projectcontactinfo.ui.models.UserContactInfoItem
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getContacts(): List<UserContactInfoItem>

}