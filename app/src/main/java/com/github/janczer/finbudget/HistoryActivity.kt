package com.github.janczer.finbudget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.github.janczer.finbudget.db.ItemDBHandler
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.row_item.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }

    override fun onStart() {
        super.onStart()
        val dbHandler = ItemDBHandler(this, null, null, 1)
        val rows = dbHandler.getAll()
        val history = findViewById(R.id.history) as TableLayout
        for (item in rows) {
            val row = LayoutInflater.from(applicationContext).inflate(R.layout.row_item, null)
            val first = row.findViewById(R.id.first) as TextView
            val second = row.findViewById(R.id.second) as TextView
            first.text = item.id.toString()
            second.text = item.amount.toString()
            history.addView(row)
        }
    }
}
