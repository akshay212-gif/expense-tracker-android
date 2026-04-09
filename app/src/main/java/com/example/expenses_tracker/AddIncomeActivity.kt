package com.example.expenses_tracker

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button


class AddIncomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.expenses_tracker.R.layout.activity_add_income)

        val etDetails = findViewById<EditText>(R.id.etDetails)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnSave = findViewById<Button>(R.id.btnSaveIncome)



        val sharedPref = getSharedPreferences("income_data", Context.MODE_PRIVATE)

        val tvIncomeList = findViewById<TextView>(R.id.tvIncomeList)

        val savedList = sharedPref.getString("income_list", "") ?: ""

        tvIncomeList.text = savedList

        btnSave.setOnClickListener {
            val details = etDetails.text.toString()
            val amount = etAmount.text.toString().toIntOrNull()

            if (details.isEmpty() || amount == null) {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get old total
            val oldTotal = sharedPref.getInt("total_income", 0)

            // Add new income
            val newTotal = oldTotal + amount

            // Save updated total
            sharedPref.edit().putInt("total_income", newTotal).apply()
            // Save income list with numbering
            val oldList = sharedPref.getString("income_list", "") ?: ""

// count existing items
            val count = if (oldList.isEmpty()) 1 else oldList.split("\n").size + 1

            val newEntry = "$count) $details - ₹$amount\n"

            val updatedList = oldList + newEntry

            sharedPref.edit().putString("income_list", updatedList).apply()

            val tvIncomeList = findViewById<TextView>(R.id.tvIncomeList)
            tvIncomeList.text = updatedList

            Toast.makeText(this, "Income Added", Toast.LENGTH_SHORT).show()


            finish() // go back to main screen
        }
    }
}