package com.example.modulo2.data.clock.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.modulo2.data.clock.data.model.ClockEntity
import com.example.modulo2.util.Constants

@Dao
interface ClockDao {
    @Query("SELECT * FROM ${Constants.DATABASE_CLOCK_TABLE} WHERE userId = :userId")
    fun getClocksByUser(userId: Long):  List<ClockEntity>

    @Insert
    suspend fun insertClock(clock: ClockEntity)

    @Update
    suspend fun updateClock(clock: ClockEntity)

    @Delete
    suspend fun delete(clock: ClockEntity)
}