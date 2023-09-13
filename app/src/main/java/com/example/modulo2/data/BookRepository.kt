package com.example.modulo2.data

import com.example.modulo2.data.remote.BooksApi
import com.example.modulo2.data.remote.model.Book
import com.example.modulo2.data.remote.model.BookDetail
import retrofit2.Call
import retrofit2.Retrofit

class BookRepository(private val retrofit: Retrofit) {
    private val booksApi: BooksApi = retrofit.create(BooksApi::class.java)

    fun getBooks(): Call<List<Book>> =
        booksApi.getBooks()

    fun getBooksDetail(id: String?): Call<BookDetail> =
        booksApi.getBookById(id)
}