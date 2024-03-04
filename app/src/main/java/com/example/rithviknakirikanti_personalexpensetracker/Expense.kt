package com.example.rithviknakirikanti_personalexpensetracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val amount: Double,
    val category: String
)

