package com.github.janczer.finbudget.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class CategoryDBHandler(context: Context, name: String?,
                        factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, Params.DATABASE_NAME,
                factory, Params.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = String.format(
                "create table %s ( " +
                        "%s integer primary key," +
                        "%s integer)",
                Params.TABLE_CATEGORY, Params.COLUMN_ID, Params.COLUMN_NAME
        )
        db?.execSQL(createTable)

        arrayOf(
                Category("Food"),
                Category("Transport"),
                Category("Entertainment"),
                Category("Health")
        ).forEach { cat -> addCategory(cat) }
    }

    fun addCategory(category: Category) {
        val values = ContentValues()
        values.put(Params.COLUMN_NAME, category.name)

        val db = this.writableDatabase

        db.insert(Params.TABLE_CATEGORY, null, values)
        db.close()
    }

    fun getAll() : ArrayList<Category> {
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(String.format("select * from %s", Params.TABLE_CATEGORY), null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        if (cursor == null) {
            return ArrayList()
        }

        if (!cursor.moveToFirst()) {
            return ArrayList()
        }

        val categories = ArrayList<Category>()
        while (!cursor.isAfterLast) {
            categories.add(
                    Category(
                            cursor.getInt(cursor.getColumnIndex(Params.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(Params.COLUMN_NAME))
                    )
            )
            cursor.moveToNext()
        }

        return categories
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(String.format("drop table if exists %s", Params.TABLE_CATEGORY))
        onCreate(db)
    }
}