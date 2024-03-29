package com.example.rithviknakirikanti_personalexpensetracker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class ExpenseListFragment : Fragment() {

    private lateinit var viewModel: ExpenseViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var dateEditText: EditText
    private lateinit var categorySpinner: Spinner

    private val expensesObserver = androidx.lifecycle.Observer<List<Expense>> { expenses ->
        val category = categorySpinner.selectedItem.toString()
        val filteredExpenses = if (category != "All") expenses.filter { it.category == category } else expenses
        (recyclerView.adapter as ExpenseAdapter).updateData(filteredExpenses.toMutableList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        dateEditText = view.findViewById(R.id.filterDateEditText)
        categorySpinner = view.findViewById(R.id.spinnerCategory)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ExpenseViewModel::class.java)

        recyclerView.adapter = ExpenseAdapter(mutableListOf()) { expense ->
            navigateToAddEditExpenseFragment(expense)
        }

        viewModel.allExpenses.observe(viewLifecycleOwner, expensesObserver)

        val fabAddExpense: FloatingActionButton = view.findViewById(R.id.fabAddExpense)
        fabAddExpense.setOnClickListener {
            navigateToAddEditExpenseFragment()
        }

        setupCategorySpinner()
        setupFilterListeners()

        return view
    }

    private fun setupCategorySpinner() {
        val categories = mutableListOf("All")
        resources.getStringArray(R.array.expense_categories).let { categories.addAll(it) }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        categorySpinner.adapter = adapter
    }

    private fun setupFilterListeners() {
        dateEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterExpenses()
            }
        })

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterExpenses()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private var currentExpensesLiveData: LiveData<List<Expense>>? = null

    private fun filterExpenses() {
        currentExpensesLiveData?.removeObservers(viewLifecycleOwner)

        val dateStr = dateEditText.text.toString()
        val category = categorySpinner.selectedItem.toString()

        currentExpensesLiveData = when {
            dateStr.isNotEmpty() -> viewModel.getExpensesForDay(getDayStart(dateStr))
            category != "All" -> viewModel.getExpensesByCategory(category)
            else -> viewModel.allExpenses
        }

        currentExpensesLiveData?.observe(viewLifecycleOwner, expensesObserver)
    }

    private fun getDayStart(dateStr: String): Long {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        return try {
            val date = sdf.parse(dateStr)?.time ?: 0L
            val calendar = Calendar.getInstance().apply {
                timeInMillis = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            calendar.timeInMillis
        } catch (e: Exception) {
            0L
        }
    }

    private fun navigateToAddEditExpenseFragment(expense: Expense? = null) {
        val fragment = AddEditExpenseFragment().apply {
            arguments = Bundle().apply {
                putSerializable("expense", expense)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
