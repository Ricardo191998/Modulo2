package com.example.modulo2.data.user.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.modulo2.data.user.data.model.UserEntity
import com.example.modulo2.util.Constants

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constants.DATABASE_USER_TABLE} WHERE email = :email")
    fun getUserByEmail(email: String):  UserEntity

    @Insert
    suspend fun insertUser(user: UserEntity) : Long

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

}