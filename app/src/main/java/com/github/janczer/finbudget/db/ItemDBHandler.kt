package com.github.janczer.finbudget.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class ItemDBHandler(context: Context, name: String?,
                    factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, Params.DATABASE_NAME,
                factory, Params.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = String.format(
                "create table %s ( " +
                        "%s integer primary key," +
                        "%s integer)",
                Params.TABLE_ITEM, Params.COLUMN_ID, Params.COLUMN_AMOUNT
        )
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(String.format("drop table if exists %s", Params.TABLE_ITEM))
        onCreate(db)
    }

    fun addItem(item: Item) {
        val values = ContentValues()
        values.put(Params.COLUMN_AMOUNT, item.amount)

        val db = this.writableDatabase

        db.insert(Params.TABLE_ITEM, null, values)
        db.close()
    }

    fun getAll() : ArrayList<Item> {
        val items = ArrayList<Item>()
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(String.format("select * from %s", Params.TABLE_ITEM), null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        if (cursor == null) {
            return ArrayList()
        }

        if (!cursor.moveToFirst()) {
            return ArrayList()
        }

        while (!cursor.isAfterLast) {
            items.add(
                    Item(
                            cursor.getInt(cursor.getColumnIndex(Params.COLUMN_ID)),
                            cursor.getInt(cursor.getColumnIndex(Params.COLUMN_AMOUNT))
                    )
            )
            cursor.moveToNext()
        }

        return items
    }
}