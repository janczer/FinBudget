package com.github.janczer.finbudget

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addAmount(view: View) {
        val addAmount = Intent(this, AddActivity::class.java)

        startActivity(addAmount)
    }

    fun showHistory(view: View) {
        val history = Intent(this, HistoryActivity::class.java)

        startActivity(history)
    }
}
