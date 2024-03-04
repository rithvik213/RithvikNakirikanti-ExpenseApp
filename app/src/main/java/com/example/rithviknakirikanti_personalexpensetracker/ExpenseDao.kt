package com.example.rithviknakirikanti_personalexpensetracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.sql.Date

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expense WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesByDate(startDate: Date, endDate: Date): List<Expense>

    @Query("SELECT * FROM expense WHERE category = :category")
    suspend fun getExpensesByCategory(category: String): List<Expense>
}
