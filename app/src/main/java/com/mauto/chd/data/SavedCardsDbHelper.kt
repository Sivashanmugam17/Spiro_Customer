package com.mauto.chd.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.mauto.chd.earningsviewmodel.SavedCardsModel
import java.util.*


class SavedCardsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME_SAVED_CARDS, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertSavedCards(user: SavedCardsModel): Boolean {

        val db = writableDatabase
        val values = ContentValues()
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_TYPE, user.paymentMethodName)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_CODE, user.paymentMethodCode)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_IS_DEFAULT, user.paymentMethodSelected)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_HOLDER_NAME, user.cardHolderName)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_NUMBER, user.cardNumber)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_TYPE, user.cardType)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_ID, user.cardId)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_MONTH, user.cardExpMonth)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_YEAR, user.cardExpYear)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED, user.cardSelected)
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_IMAGE, user.cardImage)
        db.insert(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, null, values)
        db.close()

        return true
    }





    @Throws(SQLiteConstraintException::class)
    fun deleteLastid(userid: String): Boolean {
        val db = writableDatabase
        var selection: String
        selection = SavedCardsDbContract.SavedCardsEntry.COLUMN_USER_ID + " LIKE ?"
        val selectionArgs = arrayOf(userid)
        db.delete(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun getDefaultCard(card_id: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED, "1")
        db.update(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, values, SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_ID + "='" + card_id + "'", null)
        db.close()
    }

    fun updateDefaultCard(card_id: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED, "1")
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_IS_DEFAULT, "1")
        db.update(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, values, SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_ID + "='" + card_id + "'", null)
        db.close()
    }


    fun clearDefaultCard() {
        val status: String = "1"
        val db = writableDatabase
        val values = ContentValues()
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED, "0")
        values.put(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_IS_DEFAULT, "0")
        db.update(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, values, SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED + "='" + status + "'", null)
        db.close()
    }

    fun getSavedCardsCount(): Int {
        val db = readableDatabase
        val mCount = db.rawQuery("select * from " + SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, null)
        var result: Int = mCount.getCount()
        db.close()
        return result
    }


    fun getAllSavedCards(): ArrayList<SavedCardsModel>
    {
        val savedCards = ArrayList<SavedCardsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + SavedCardsDbContract.SavedCardsEntry.TABLE_NAME + " ORDER BY id ASC", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var payment_method_type: String
        var payment_method_code: String
        var payment_method_is_default: String

        var card_holder_name: String
        var card_number: String
        var card_type: String
        var card_id: String
        var exp_month: String
        var exp_year: String
        var is_default_card: String
        var image: String


        var dbid: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dbid = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_USER_ID))

                payment_method_type = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_TYPE))
                payment_method_code = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_CODE))
                payment_method_is_default = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_IS_DEFAULT))

                card_holder_name = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_HOLDER_NAME))
                card_number = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_NUMBER))
                card_type = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_TYPE))
                card_id = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_ID))
                exp_month = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_MONTH))
                exp_year = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_YEAR))
                is_default_card = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED))
                image = cursor.getString(cursor.getColumnIndex(SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_IMAGE))

                if(!card_number.equals("") && !card_id.equals("") && !exp_month.equals(""))
                {
                    savedCards.add(SavedCardsModel(payment_method_type, payment_method_code, payment_method_is_default, card_holder_name, card_number, card_type, card_id, exp_month, exp_year, is_default_card, image))
                }
                cursor.moveToNext()
            }
        }
        return savedCards
    }


    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME_SAVED_CARDS = "savehdcarouids.db"
        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + SavedCardsDbContract.SavedCardsEntry.TABLE_NAME + " (" +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_TYPE + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_CODE + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_PAYMENT_METHOD_IS_DEFAULT + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_HOLDER_NAME + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_NUMBER + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_TYPE + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_ID + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_MONTH + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_EXP_YEAR + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_SELECTED + " TEXT," +
                        SavedCardsDbContract.SavedCardsEntry.COLUMN_CARD_IMAGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SavedCardsDbContract.SavedCardsEntry.TABLE_NAME
    }


    fun clearTable() {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM " + SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            if (count > 0) {
                db.delete(SavedCardsDbContract.SavedCardsEntry.TABLE_NAME, null, null)
                db.close()
            }
            cursor.close()
        }
    }

}