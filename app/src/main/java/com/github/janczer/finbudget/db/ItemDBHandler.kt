package com.github.janczer.finbudget.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class ItemDBHandler(context: Context, name: String?,
                    factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME,
                factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ITEM_TABLE = String.format(
                "create table %s ( " +
                        "%s integer primary key," +
                        "%s integer)",
                TABLE_ITEM, COLUMN_ID, COLUMN_AMOUNT
        )
        db?.execSQL(CREATE_ITEM_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(String.format("drop table if exists %s", TABLE_ITEM))
        onCreate(db)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "fin_budget.db"
        val TABLE_ITEM = "item"

        val COLUMN_ID = "_id"
        val COLUMN_AMOUNT = "amount"
    }

    fun addItem(item: Item) {
        val values = ContentValues()
        values.put(COLUMN_AMOUNT, item.amount)

        val db = this.writableDatabase

        db.insert(TABLE_ITEM, null, values)
        db.close()
    }

    fun getAll() : ArrayList<Item> {
        val items = ArrayList<Item>()
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(String.format("select * from %s", TABLE_ITEM), null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var id: Int
        var amount: Int
        if (!cursor!!.moveToFirst()) {
            return ArrayList()
        }

        while (!cursor.isAfterLast) {
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
            items.add(Item(id, amount))
            cursor.moveToNext()
        }

        return items
    }
}