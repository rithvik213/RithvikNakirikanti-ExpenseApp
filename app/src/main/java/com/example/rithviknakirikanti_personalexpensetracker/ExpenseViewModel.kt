package com.example.rithviknakirikanti_personalexpensetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ExpenseDatabase.getDatabase(application)
    private val expenseDao = db.expenseDao()

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    private val _filteredExpenses = MutableLiveData<List<Expense>>()
    val filteredExpenses: LiveData<List<Expense>> = _filteredExpenses

    fun insert(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        expenseDao.insert(expense)
    }

    fun filterExpensesByDate(startDate: Long, endDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _filteredExpenses.postValue(expenseDao.getExpensesByDate(startDate, endDate).value)
        }
    }

    fun filterExpensesByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _filteredExpenses.postValue(expenseDao.getExpensesByCategory(category).value)
        }
    }
}
