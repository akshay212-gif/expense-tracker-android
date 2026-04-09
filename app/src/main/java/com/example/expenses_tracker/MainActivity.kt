package com.example.expenses_tracker
import android.graphics.Color
import android.content.Context
import android.widget.TextView


import android.widget.ImageView

import android.content.Intent

import android.widget.Toast
import android.widget.Button
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        // 👇 ADD THIS
        val btnViewAll = findViewById<Button>(R.id.btnViewAll)
        val btnBudget = findViewById<Button>(R.id.btnBudget)

        btnViewAll.setOnClickListener {
            val intent = Intent(this, AllExpensesActivity::class.java)
            startActivity(intent)
        }

        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddExpense)

        fab.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        fab.setOnClickListener {
            fab.setOnClickListener {
                val intent = Intent(this, AddExpenseActivity::class.java)
                startActivity(intent)
            }
        } //for + icon

        btnBudget.setOnClickListener {
            Toast.makeText(this, "Budget Clicked", Toast.LENGTH_SHORT).show()
        }
        val btnAddIncome = findViewById<ImageView>(R.id.btnAddIncome)

        btnAddIncome.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
        }
        val sharedPref = getSharedPreferences("income_data", Context.MODE_PRIVATE)
        val totalIncome = sharedPref.getInt("total_income", 0)

        val tvIncome = findViewById<TextView>(R.id.tvTotalIncome) // your TextView ID
        tvIncome.text = "₹ $totalIncome"
    }
    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("income_data", Context.MODE_PRIVATE)
        val totalIncome = sharedPref.getInt("total_income", 0)

        val tvIncome = findViewById<TextView>(R.id.tvTotalIncome)
        tvIncome.text = "₹ $totalIncome"

        val expensePref = getSharedPreferences("expense_data", Context.MODE_PRIVATE)
        val expenseList = expensePref.getString("expense_list", "") ?: ""



        val tvExpenses = findViewById<TextView>(R.id.tvEmptyState)
        tvExpenses.text =  expenseList

        val totalExpense = expensePref.getInt("total_expense", 0)

        val tvTotalSpent = findViewById<TextView>(R.id.tvMonthlyTotal)
        tvTotalSpent.text = "₹ $totalExpense"

        val remaining = totalIncome - totalExpense

        val tvBudget = findViewById<TextView>(R.id.tvBudget)
        tvBudget.text = "₹ $remaining"
        if (remaining >= 0) {
            tvBudget.setTextColor(Color.parseColor("#4CAF50")) // green
        } else {
            tvBudget.setTextColor(Color.RED) // overspending
        }
    }
}