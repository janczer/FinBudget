package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.janczer.finbudget.db.Category
import com.github.janczer.finbudget.db.CategoryDBHandler
import com.github.janczer.finbudget.db.ItemDBHandler
import kotlinx.android.synthetic.main.activity_add.*
import com.github.janczer.finbudget.db.Item

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    override fun onStart() {
        super.onStart()
        val dbHandler = CategoryDBHandler(this, null, null, 1)
        val rows = dbHandler.getAll()
        val spinner = spinnerCategory
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayOf("test1", "test2"))
    }

    fun newItem(view: View) {
        val dbHandler = ItemDBHandler(this, null, null, 1)
        if (editAmount.text.isEmpty()) {
            val toast = Toast.makeText(this, "Amount can't be empty", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        val amount = Integer.parseInt(editAmount.text.toString())
        val item = Item(amount)
        dbHandler.addItem(item)

        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
    }
}
