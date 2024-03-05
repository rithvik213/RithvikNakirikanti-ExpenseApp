package com.example.rithviknakirikanti_personalexpensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class ExpenseAdapter(private var expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int = expenses.size

    fun updateData(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)

        fun bind(expense: Expense) {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            dateTextView.text = sdf.format(expense.date)
            amountTextView.text = String.format("$%.2f", expense.amount)
            categoryTextView.text = expense.category
        }
    }
}
