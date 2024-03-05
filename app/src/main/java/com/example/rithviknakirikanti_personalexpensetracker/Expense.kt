package com.example.rithviknakirikanti_personalexpensetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val amount: Double,
    val category: String
) : Serializable
