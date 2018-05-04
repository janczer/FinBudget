package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import com.github.janczer.finbudget.db.AppDBHandler
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHandler = AppDBHandler(this, null, null, 1)
        val current = SimpleDateFormat.getDateInstance().format(Calendar.getInstance().time)
        val totalToday = dbHandler.getAllItems().filter {item -> item.date == current }
        val categories = dbHandler.getAllCategories()
        val mainTable = findViewById(R.id.main_table) as TableLayout
        for ((_, category) in categories) {
            val row = LayoutInflater.from(applicationContext).inflate(R.layout.main_row_category, null)
            val nameOfCategory = row.findViewById(R.id.nameOfCategory) as TextView
            val totalForCategory = row.findViewById(R.id.totalForCategory) as TextView
            nameOfCategory.text = category.name
            totalForCategory.text = totalToday.filter { item -> item.idcategory == category.id }.sumBy { it.amount }.toString()
            mainTable.addView(row)
        }
        totalAmount.text = totalToday.sumBy { it.amount }.toString()
    }

    fun addAmount(view: View) = startActivity(Intent(this, AddActivity::class.java))

    fun showHistory(view: View) = startActivity(Intent(this, HistoryActivity::class.java))
}
