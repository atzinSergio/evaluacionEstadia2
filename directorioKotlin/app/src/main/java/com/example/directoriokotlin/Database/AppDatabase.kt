package com.example.directoriokotlin.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.directoriokotlin.Interface.ContactosDao
import com.example.directoriokotlin.Modelos.Contacto

@Database(entities = [Contacto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactos() : ContactosDao

    companion object{
        @Volatile
        private  var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    INSTANCE = instance
                    return  instance
                }
            }
        }
    }

}