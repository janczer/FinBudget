package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import com.github.janczer.finbudget.db.AppDBHandler
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }

    override fun onBackPressed() = startActivity(Intent(this, MainActivity::class.java))

    override fun onStart() {
        super.onStart()
        val dbHandler = AppDBHandler(this, null, null, 1)
        val rows = dbHandler.getAllItems()
        val history = findViewById(R.id.history) as TableLayout
        val categories = dbHandler.getAllCategories()
        val current = SimpleDateFormat.getDateInstance().format(Calendar.getInstance().time)
        for (item in rows) {
            val row = LayoutInflater.from(applicationContext).inflate(R.layout.row_item, null)
            if (current != item.date) {
                row.setBackgroundResource(R.color.colorSecondaryText)
            }
            val first = row.findViewById(R.id.first) as TextView
            val second = row.findViewById(R.id.second) as TextView
            val third = row.findViewById(R.id.third) as TextView
            val four = row.findViewById(R.id.four) as TextView
            first.text = item.id.toString()
            second.text = item.amount.toString()
            third.text = categories[item.idcategory]?.name
            four.text = item.date
            row.setOnClickListener {
                showOneItem(
                        view = View(applicationContext),
                        id = first.text.toString(),
                        amount = second.text.toString(),
                        category = third.text.toString(),
                        date = four.text.toString()
                )
            }
            history.addView(row)
        }
    }

    private fun showOneItem(view: View, id: String, amount: String, category: String, date: String) {
        val oneItem = Intent(this, ShowOneItemActivity::class.java)
        oneItem.putExtra("id", id)
        oneItem.putExtra("amount", amount)
        oneItem.putExtra("category", category)
        oneItem.putExtra("date", date)
        startActivity(oneItem)
    }
}
