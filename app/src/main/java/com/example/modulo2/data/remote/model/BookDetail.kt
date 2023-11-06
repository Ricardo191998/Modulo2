package com.example.modulo2.data.remote.model

import com.google.gson.annotations.SerializedName

data class BookDetail(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null,
    @SerializedName("videoUrl")
    var videoUrl: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("editorial")
    var editorial: String? = null,
    @SerializedName("pages")
    var pages: String? = null,
    @SerializedName("isbn")
    var isbn: String? = null
)