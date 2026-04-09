package com.example.expenses_tracker

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_expense)

        val etDetails = findViewById<EditText>(R.id.etExpenseDetails)
        val etAmount = findViewById<EditText>(R.id.etExpenseAmount)
        val btnSave = findViewById<Button>(R.id.btnSaveExpense)

        val sharedPref = getSharedPreferences("expense_data", Context.MODE_PRIVATE)

        btnSave.setOnClickListener {

            val details = etDetails.text.toString()
            val amount = etAmount.text.toString().toIntOrNull()

            if (details.isEmpty() || amount == null) {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Total expense update
            val oldTotal = sharedPref.getInt("total_expense", 0)
            val newTotal = oldTotal + amount
            sharedPref.edit().putInt("total_expense", newTotal).apply()

            // Expense list save
            val oldList = sharedPref.getString("expense_list", "") ?: ""
            val count = oldList.split("\n")
                .filter { it.isNotEmpty() }
                .size + 1

            val newEntry = "• $details - ₹$amount\n"
            val updatedList = oldList + newEntry

            sharedPref.edit().putString("expense_list", updatedList).apply()

            Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}