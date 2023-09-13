package com.example.modulo2.data.remote.model

import com.google.gson.annotations.SerializedName

data class Book (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("author")
    var author: String? = null
)