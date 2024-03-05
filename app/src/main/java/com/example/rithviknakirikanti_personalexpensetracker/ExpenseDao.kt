package com.example.rithviknakirikanti_personalexpensetracker
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

   @Query("SELECT * FROM expense WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDate(startDate: Long, endDate: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): LiveData<List<Expense>>
}
