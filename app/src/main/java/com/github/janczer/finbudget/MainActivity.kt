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
import com.github.janczer.finbudget.db.Category
import com.github.janczer.finbudget.db.Item
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.text.NumberFormat
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
        val totalToday = dbHandler.getAllItems().filter { item -> item.date == current }
        val mainTable = findViewById(R.id.main_table) as TableLayout
        dbHandler.getAllCategories().forEach {
            mainTable.addView(
                    createRowForTable(it.value, totalToday)
            )
        }
        totalAmount.text = totalToday.sumBy { it.amount }.toCurrencyFormat()
    }

    private fun createRowForTable(category: Category, totalToday: List<Item>): View? {
        val row = LayoutInflater.from(applicationContext).inflate(R.layout.main_row_category, null)
        val nameOfCategory = row.findViewById(R.id.nameOfCategory) as TextView
        val totalForCategory = row.findViewById(R.id.totalForCategory) as TextView
        nameOfCategory.text = category.name
        totalForCategory.text =
                totalToday.
                        filter { item -> item.idcategory == category.id }.
                        sumBy { it.amount }.
                        toCurrencyFormat()
        return row
    }

    fun addAmount(view: View) = startActivity(Intent(this, AddActivity::class.java))

    fun showHistory(view: View) = startActivity(Intent(this, HistoryActivity::class.java))
}

private fun <E> Collection<E>.sumBy(selector: (E) -> BigDecimal): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

private fun BigDecimal.toCurrencyFormat() = NumberFormat.getCurrencyInstance().format(this)