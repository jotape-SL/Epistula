package com.example.epistula.objects

import com.example.epistula.model.Lembrete

object DadosMockados {
    val lembretes = mutableListOf(
        Lembrete(1, "2024-06-17", "Reunião Final"),
        Lembrete(2, "2024-07-01", "Inicio das Férias"),
        Lembrete(3, "2024-10-12", "Dia das Crianças")
    )

    fun adicionarLembrete(lembrete: Lembrete){
        lembretes.add(lembrete)
    }
}