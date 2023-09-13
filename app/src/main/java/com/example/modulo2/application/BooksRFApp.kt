package com.example.modulo2.application

import android.app.Application
import com.example.modulo2.data.BookRepository
import com.example.modulo2.data.remote.RetrofitHelper

class BooksRFApp: Application() {
    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy{
        BookRepository(retrofit)
    }
}