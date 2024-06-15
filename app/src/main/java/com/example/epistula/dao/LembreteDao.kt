package com.example.epistula.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.epistula.model.Lembrete

@Dao
interface LembreteDao {
    @Query("SELECT * FROM lembretes")
    suspend fun getAll(): List<Lembrete>

    @Insert
    suspend fun insert(lembrete: Lembrete)
}