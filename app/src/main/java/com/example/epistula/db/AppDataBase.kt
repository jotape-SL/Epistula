package com.example.epistula.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.epistula.dao.LembreteDao
import com.example.epistula.model.Lembrete

@Database(entities = [Lembrete::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun lembreteDao(): LembreteDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "lembrete_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}