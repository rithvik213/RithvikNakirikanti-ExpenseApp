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

    fun getExpensesByDate(startDate: Long, endDate: Long): LiveData<List<Expense>> {
        return expenseDao.getExpensesByDate(startDate, endDate)
    }

    fun getExpensesByCategory(category: String): LiveData<List<Expense>> {
        return expenseDao.getExpensesByCategory(category)
    }
}
