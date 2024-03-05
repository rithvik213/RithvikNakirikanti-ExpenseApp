package com.example.rithviknakirikanti_personalexpensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rithviknakirikanti_personalexpensetracker.databinding.FragmentAddEditExpenseBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEditExpenseFragment : Fragment() {

    private var _binding: FragmentAddEditExpenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExpenseViewModel
    private var currentExpense: Expense? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditExpenseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        setupCategorySpinner()
        setupListeners()

        arguments?.getSerializable("expense")?.let {
            currentExpense = it as Expense
            populateFields(currentExpense!!)
        }


        binding.buttonDelete.visibility = if (currentExpense != null) View.VISIBLE else View.GONE

        return binding.root
    }

    private fun setupCategorySpinner() {
        val categories = resources.getStringArray(R.array.expense_categories)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerCategory.adapter = adapter

        currentExpense?.let {
            val categoryPosition = adapter.getPosition(it.category)
            binding.spinnerCategory.setSelection(categoryPosition)
        }
    }

    private fun populateFields(expense: Expense) {
        binding.editTextAmount.setText(expense.amount.toString())
        val categories = resources.getStringArray(R.array.expense_categories)
        val categoryPosition = categories.indexOf(expense.category)
        if (categoryPosition >= 0) {
            binding.spinnerCategory.setSelection(categoryPosition)
        }
        binding.editTextDate.setText(SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date(expense.date)))
    }


    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            saveExpense()
        }

        binding.buttonCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonDelete.setOnClickListener {
            deleteExpense()
        }
    }

    private fun deleteExpense() {
        currentExpense?.let { expense ->
            viewModel.delete(expense)
            Toast.makeText(context, "Expense deleted", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun saveExpense() {
        val amountText = binding.editTextAmount.text.toString()
        val category = binding.spinnerCategory.selectedItem.toString()
        val dateString = binding.editTextDate.text.toString()

        val amount = amountText.toDoubleOrNull()
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val date = sdf.parse(dateString)?.time

        if (amount == null || date == null) {
            Toast.makeText(context, "Please enter valid data.", Toast.LENGTH_SHORT).show()
            return
        }

        val expenseToSave = Expense(
            id = currentExpense?.id ?: 0,
            date = date ?: 0L,
            amount = amount ?: 0.0,
            category = category
        )

        if (currentExpense == null) {
            viewModel.insert(expenseToSave)
        } else {
            viewModel.insert(expenseToSave)
        }

        Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
