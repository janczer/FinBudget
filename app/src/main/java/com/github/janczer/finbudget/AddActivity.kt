package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.janczer.finbudget.db.AppDBHandler
import kotlinx.android.synthetic.main.activity_add.*
import com.github.janczer.finbudget.db.Item
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    override fun onStart() {
        super.onStart()
        val dbHandler = AppDBHandler(this, null, null, 1)
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ArrayList(dbHandler.getAllCategories().values))
    }

    fun newItem(view: View) {
        val dbHandler = AppDBHandler(this, null, null, 1)
        if (editAmount.text.isEmpty()) {
            showToast("Amount can't be empty")
            return
        }

        val categories = dbHandler.getAllCategories()
        val amount = BigDecimal(editAmount.text.toString())

        if (amount < BigDecimal.ZERO) {
            showToast("Amount can't be negative")
            return
        }
        val catName = spinnerCategory.selectedItem.toString()

        val item = Item(amount)
        item.idcategory = categories.filter { it.value.name == catName }.keys.first()
        item.date = Calendar.getInstance().time.toStringFormatLocal()
        dbHandler.addItem(item)

        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
    }

    override fun onBackPressed() = startActivity(Intent(this, MainActivity::class.java))

    private fun showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view.setBackgroundResource(R.drawable.toast_error)
        toast.show()
    }
}

private fun Date.toStringFormatLocal() = SimpleDateFormat.getDateInstance().format(this)
