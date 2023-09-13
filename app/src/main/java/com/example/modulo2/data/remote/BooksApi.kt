package com.example.modulo2.data.remote
import com.example.modulo2.data.remote.model.Book
import com.example.modulo2.data.remote.model.BookDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {
    @GET("books")
    fun getBooks(): Call<List<Book>>

    @GET("book/{id}")
    fun getBookById(
        @Path("id") id: String?
    ): Call<BookDetail>
}