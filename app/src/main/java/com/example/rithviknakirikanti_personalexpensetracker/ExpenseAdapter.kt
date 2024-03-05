package com.example.rithviknakirikanti_personalexpensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(private var expenses: List<Expense>, private val onClick: (Expense) -> Unit) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int = expenses.size

    fun updateData(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    class ExpenseViewHolder(itemView: View, val onClick: (Expense) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)

        fun bind(expense: Expense) {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            dateTextView.text = sdf.format(Date(expense.date))
            amountTextView.text = String.format("$%.2f", expense.amount)
            categoryTextView.text = expense.category

            itemView.setOnClickListener {
                onClick(expense)
            }
        }
    }
}
