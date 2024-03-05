package com.example.rithviknakirikanti_personalexpensetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao: ExpenseDao
    val allExpenses: LiveData<List<Expense>>

    init {
        val db = ExpenseDatabase.getDatabase(application)
        expenseDao = db.expenseDao()
        allExpenses = expenseDao.getAllExpenses()
    }

    fun insert(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.insert(expense)
        }
    }

    fun delete(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.delete(expense)
        }
    }

    fun getExpensesForDay(date: Long): LiveData<List<Expense>> {
        val dayStart = date
        val dayEnd = dayStart + 86_400_000
        return expenseDao.getExpensesForDay(dayStart, dayEnd)
    }

    fun getExpensesByDateAndCategory(date: Long, category: String): LiveData<List<Expense>> {
        return expenseDao.getExpensesByDateAndCategory(date, if (category == "All") "%" else category)
    }

    fun getExpensesByCategory(category: String): LiveData<List<Expense>> {
        return expenseDao.getExpensesByCategory(category)
    }
}
