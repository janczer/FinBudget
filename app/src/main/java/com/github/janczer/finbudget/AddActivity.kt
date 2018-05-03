package com.github.janczer.finbudget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.janczer.finbudget.db.ItemDBHandler
import kotlinx.android.synthetic.main.activity_add.*
import com.github.janczer.finbudget.db.Item

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun newItem(view: View) {
        val dbHandler = ItemDBHandler(this, null, null, 1)
        val amount = Integer.parseInt(editAmount.text.toString())

        val item = Item(amount)
        dbHandler.addItem(item)
    }
}
