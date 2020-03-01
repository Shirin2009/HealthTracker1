package com.example.healthtracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
//extend to SQLiteOpenHelper to manage database creation and version management
class DatabaseHelper(context: Context?):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    //create table SQL query
    private val CREATE_TABLE_USER = (" CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    //drop table sql query
    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_USER"

    //this function is called once ( the first time of the execution), it creates the database table using SQL query
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
    }

    //this method will be called when we change the database version.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //drop table if already exist
        db.execSQL(DROP_USER_TABLE)
        //create table again
        onCreate(db)
    }

    //this method is for insert a data into database table
    fun insertUserData(name: String, email: String, password: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_USER_PASSWORD, password)

        //inserting row into database
        db.insert(TABLE_USER, null, contentValues)
        db.close()
    }

    fun userPresents(email: String, password: String):Boolean{
    val db=writableDatabase
    val query= "select * from users where email = '$email' and password = '$password' "
    val cursor = db.rawQuery(query,null)
    if(cursor.count <= 0){
    cursor.close()
    return false
    }
    cursor.close()
    return true
    }

    fun isUserExists(email: String, password: String): Boolean {
        val db = this.readableDatabase

        // array of columns to fetch
        val columns = arrayOf(COLUMN_ID)

        //selection criteria
        val selection = "$COLUMN_EMAIL=?"

        //selection arguments
        val selectionArgs = arrayOf(email,password)

        //check if the user exist (query function fetches records from user table in our database)
        val cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if(cursorCount>0){
            return true
        }
        return false
    }


    //updating user record
    fun updateUser(users: UserModel) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_NAME, users.fullName)
        values.put(COLUMN_EMAIL, users.email)
        values.put(COLUMN_USER_PASSWORD, users.password)

        // updating row
        db.update(TABLE_USER, values, "$COLUMN_ID =?", arrayOf(users.id.toString()))
        db.close()
    }

    fun deleteUserData(users:UserModel){
        val db = this.writableDatabase
        db.delete(TABLE_USER, "COLUMN_ID=?", arrayOf(users.id.toString()))
        db.close()
    }



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




