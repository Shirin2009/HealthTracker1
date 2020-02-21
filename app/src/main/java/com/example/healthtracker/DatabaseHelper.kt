package com.example.healthtracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    //create table SQL query
    private val USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    //drop table sql query
    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    //this function is called once ( the first time of the execution), it creates the database table using SQL query


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_USER " +
                    "($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_EMAIL TEXT)"
        )
    }

    //this method will be called when we change the database version.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //drop table if already exist
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        //create table again
        onCreate(db)
    }
/*
    //this method is for insert a data into database table
    fun insertUser(thename: String, theemail: String, thepassword: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, thename)
        contentValues.put(COLUMN_EMAIL, theemail)
        contentValues.put(COLUMN_USER_PASSWORD, thepassword)

        db.insert(TABLE_USER, null, contentValues)
    }

    fun updateData(theid: String, thename: String, theemail: String, thepassword: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, theid)
        contentValues.put(COLUMN_NAME, thename)
        contentValues.put(COLUMN_EMAIL, theemail)
        contentValues.put(COLUMN_USER_PASSWORD, thepassword)

        db.update(TABLE_USER, contentValues, "ID=?", arrayOf(theid))
        return true
    }

    fun deleteData(theid: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USER, "ID=?", arrayOf(theid))
    }

    fun isEmailExists(email: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.query(
            TABLE_USER,
            arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_USER_PASSWORD
            ), "$COLUMN_EMAIL=?",
            arrayOf(email),
            null,
            null,
            null
        )
        //if cursor has value then in user database there is user associated with this given email so return true
        return if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            return true
        } else false //if email does not exist return false
        //close()
    }*/

    companion object {
        //database version
        const val DATABASE_VERSION = 1
        //database name
        const val DATABASE_NAME = "user.db"
        //table name
        const val TABLE_USER = "users"
        //ID column @primary key
        const val COLUMN_ID = "id"
        //column names for user details
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
    }
}




