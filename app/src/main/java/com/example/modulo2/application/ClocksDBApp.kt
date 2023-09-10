package com.example.modulo2.application
import android.app.Application
import com.example.modulo2.data.clock.ClockRepository
import com.example.modulo2.data.db.ClockDatabase
import com.example.modulo2.data.user.UserRepository

class ClocksDBApp(): Application() {
    private val database by lazy {
        ClockDatabase.getDatabase(this@ClocksDBApp)
    }

    val repositoryUser by lazy {
        UserRepository(database.userDao())
    }

    val repositoryClock by lazy {
        ClockRepository(database.clockDao())
    }
}