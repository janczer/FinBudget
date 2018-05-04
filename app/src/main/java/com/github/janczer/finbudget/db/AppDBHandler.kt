package com.github.janczer.finbudget.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AppDBHandler(context: Context, name: String?,
                   factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, Params.DATABASE_NAME,
                factory, Params.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableItem = "CREATE TABLE ${Params.TABLE_ITEM} ( " +
                        "${Params.COLUMN_ID} INTEGER PRIMARY KEY," +
                        "${Params.COLUMN_AMOUNT} INTEGER," +
                        "${Params.COLUMN_IDCATEGORY} INTEGER," +
                        "${Params.COLUMN_DATE} TEXT," +
                        "FOREIGN KEY(${Params.COLUMN_IDCATEGORY}) REFERENCES ${Params.TABLE_CATEGORY}(${Params.COLUMN_ID}))"
        db?.execSQL(createTableItem)

        val createTableCat = "CREATE TABLE ${Params.TABLE_CATEGORY} ( " +
                        "${Params.COLUMN_ID} INTEGER PRIMARY KEY," +
                        "${Params.COLUMN_NAME} TEXT)"
        db?.execSQL(createTableCat)

        arrayOf(
                Category("Food"),
                Category("Transport"),
                Category("Entertainment"),
                Category("Health")
        ).forEach { cat ->
            run {
                val values = ContentValues()
                values.put(Params.COLUMN_NAME, cat.name)

                db?.insert(Params.TABLE_CATEGORY, null, values)
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Params.TABLE_ITEM}")
        db?.execSQL("DROP TABLE IF EXISTS ${Params.TABLE_CATEGORY}")
        onCreate(db)
    }

    fun addItem(item: Item) {
        val values = ContentValues()
        values.put(Params.COLUMN_AMOUNT, item.amount.toFloat())
        values.put(Params.COLUMN_IDCATEGORY, item.idcategory)
        values.put(Params.COLUMN_DATE, item.date)

        val db = this.writableDatabase

        db.insert(Params.TABLE_ITEM, null, values)
        db.close()
    }

    fun getAllItems(): ArrayList<Item> {
        val items = ArrayList<Item>()
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${Params.TABLE_ITEM} ORDER BY ${Params.COLUMN_DATE} DESC", null)
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
                            cursor.getInt(cursor.getColumnIndex(Params.COLUMN_AMOUNT)),
                            cursor.getInt(cursor.getColumnIndex(Params.COLUMN_IDCATEGORY)),
                            cursor.getString(cursor.getColumnIndex(Params.COLUMN_DATE))
                    )

            )
            cursor.moveToNext()
        }

        return items
    }

    fun getAllCategories(): Map<Int, Category> {
        val db = this.writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM ${Params.TABLE_CATEGORY}", null)
        } catch (e: SQLiteException) {
            return HashMap()
        }

        if (cursor == null) {
            return HashMap()
        }

        if (!cursor.moveToFirst()) {
            return HashMap()
        }

        val categories = HashMap<Int, Category>()
        while (!cursor.isAfterLast) {
            val id = cursor.getInt(cursor.getColumnIndex(Params.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(Params.COLUMN_NAME))
            categories[id] = Category(id, name)
            cursor.moveToNext()
        }

        return categories
    }

    fun deleteItem(idItem: Int) =
            this.writableDatabase?.execSQL("DELETE FROM ${Params.TABLE_ITEM} where ${Params.COLUMN_ID} = $idItem")
}
