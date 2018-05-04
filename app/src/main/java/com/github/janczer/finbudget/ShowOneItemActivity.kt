package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.janczer.finbudget.db.AppDBHandler
import kotlinx.android.synthetic.main.activity_show_one_item.*

class ShowOneItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_one_item)
        idItem.text = intent.getStringExtra("id")
        amount.text = intent.getStringExtra("amount")
        category.text = intent.getStringExtra("category")
        date.text = intent.getStringExtra("date")
    }

    fun deleteItem(view: View) {
        val dbHandler = AppDBHandler(this, null, null, 1)
        val idItem = Integer.parseInt(idItem.text.toString())
        dbHandler.deleteItem(idItem)
        val history = Intent(this, HistoryActivity::class.java)
        startActivity(history)
    }

    override fun onBackPressed() = startActivity(Intent(this, HistoryActivity::class.java))
}
