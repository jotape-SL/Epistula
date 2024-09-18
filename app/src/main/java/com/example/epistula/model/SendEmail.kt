package com.example.epistula.model

data class SendEmail(
    val recipient: String,
    val title: String,
    val body: String
)