package com.example.modulo2.data.clock

import com.example.modulo2.data.clock.data.ClockDao
import com.example.modulo2.data.clock.data.model.ClockEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClockRepository(private val clockDao: ClockDao) {
    suspend fun getClocks(userId: Long) : List<ClockEntity> {
        return withContext(Dispatchers.IO) {
            clockDao.getClocksByUser(userId)
        }
    }

    suspend fun insertClock(clock: ClockEntity){
        clockDao.insertClock(clock)
    }

    suspend fun updateClock(clock: ClockEntity){
        clockDao.updateClock(clock)
    }

    suspend fun deleteCloc(clock: ClockEntity){
        return withContext(Dispatchers.IO) {
            clockDao.delete(clock)
        }
    }
}