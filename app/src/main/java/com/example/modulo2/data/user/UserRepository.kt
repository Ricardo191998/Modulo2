package com.example.modulo2.data.user

import android.util.Log
import com.example.modulo2.data.user.data.UserDao
import com.example.modulo2.data.user.data.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun getUser(email: String) : UserEntity {
        return withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }
    }

    suspend fun insertUser(user: UserEntity): Long{
        return withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun updateUser(user: UserEntity){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UserEntity){
        userDao.delete(user)
    }

}