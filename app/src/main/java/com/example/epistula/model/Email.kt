package com.example.epistula.model

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("sender") val sender: String,
    @SerializedName("status") val status: String
)
