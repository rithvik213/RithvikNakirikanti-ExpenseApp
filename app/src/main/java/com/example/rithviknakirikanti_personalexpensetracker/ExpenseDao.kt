package com.example.rithviknakirikanti_personalexpensetracker
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM Expense WHERE date >= :dayStart AND date < :dayEnd")
    fun getExpensesForDay(dayStart: Long, dayEnd: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): LiveData<List<Expense>>
    @Query("SELECT * FROM Expense WHERE date = :date AND category LIKE :category")
    fun getExpensesByDateAndCategory(date: Long, category: String): LiveData<List<Expense>>
}
