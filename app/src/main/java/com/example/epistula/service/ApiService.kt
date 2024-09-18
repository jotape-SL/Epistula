package com.example.epistula.service

import com.example.epistula.model.Email
import com.example.epistula.model.SendEmail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/emails")
    suspend fun getEmails(): List<Email>

    @POST("api/emails/send")
    suspend fun sendEmail(
        @Body sendEmail: SendEmail
    ): Response<Unit>
}