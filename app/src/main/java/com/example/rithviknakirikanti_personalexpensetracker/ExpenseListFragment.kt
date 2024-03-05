package com.example.rithviknakirikanti_personalexpensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpenseListFragment : Fragment() {

    private lateinit var viewModel: ExpenseViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewExpenses)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ExpenseViewModel::class.java)

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            recyclerView.adapter = ExpenseAdapter(expenses) { expense ->
                navigateToAddEditExpenseFragment(expense)
            }
        }

        val fabAddExpense: FloatingActionButton = view.findViewById(R.id.fabAddExpense)
        fabAddExpense.setOnClickListener {
            navigateToAddEditExpenseFragment()
        }

        return view
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
