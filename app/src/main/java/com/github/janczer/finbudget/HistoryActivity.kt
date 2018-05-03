package com.github.janczer.finbudget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.row_item.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }

    override fun onStart() {
        super.onStart()
        val history = findViewById(R.id.history) as TableLayout
//        for (i in 1..10) {
//            val tableRow = TableRow(applicationContext)
//            val tw1 = TextView(applicationContext)
//            tw1.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT)
//            val tw2 = TextView(applicationContext)
//            tw2.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT)
//            tw1.setText(i.toString())
//            tw2.setText(i.toString())
//            tableRow.addView(tw1)
//            tableRow.addView(tw2)
//            history.addView(tableRow)
//        }
        var row = LayoutInflater.from(applicationContext).inflate(R.layout.row_item, null)
        val first = row.findViewById(R.id.first) as TextView
        val second = row.findViewById(R.id.second) as TextView
        first.text = "fist"
        first.text = "second"
        history.addView(row)

        // https://stackoverflow.com/questions/5183968/how-to-add-rows-dynamically-into-table-layout
    }

//    <TableRow
//    android:background="#ECEFF1"
//    android:padding="5dp">
//
//    <TextView
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    android:layout_weight="1"
//    android:text="5.0" />
//
//    <TextView
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    android:layout_weight="1"
//    android:text="Android Lollipop" />
//    </TableRow>
}
