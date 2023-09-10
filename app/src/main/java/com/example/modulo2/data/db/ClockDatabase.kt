package com.example.modulo2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.modulo2.data.clock.data.ClockDao
import com.example.modulo2.data.clock.data.model.ClockEntity
import com.example.modulo2.data.user.data.UserDao
import com.example.modulo2.data.user.data.model.UserEntity
import com.example.modulo2.util.Constants

@Database(
    entities = [UserEntity::class, ClockEntity::class],
    version = 1
)
abstract class ClockDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun clockDao() : ClockDao

    // Sin inyección de dependencias patron singleton
    companion object{
        @Volatile //lo que se escriba en este campo, será inmediatamente visible a otros hilos
        private var INSTANCE: ClockDatabase? = null

        fun getDatabase(context: Context): ClockDatabase{
            return INSTANCE?: synchronized(this){
                //Si la instancia no es nula, entonces se regresa
                // si es nula, entonces se crea la base de datos (patrón singleton)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ClockDatabase::class.java,
                    Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration() //Permite a Room recrear las tablas de la BD si las migraciones no se encuentran
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }

}