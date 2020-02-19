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
    private val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_USER"
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


    //this method is for insert a new user into database table
    fun insertUser(user:UserModel) {//get writable database
        val db=this.writableDatabase

        //create content values to insert
        val values = ContentValues()
        //put data in @values
        values.put(COLUMN_ID, user.id)
        values.put(COLUMN_NAME, user.fullName)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        //insert row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun Authenticate(user:UserModel):UserModel?{
        val db= this.writableDatabase
        val cursor = db.query(
            USER_TABLE,
            arrayOf(
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_USER_PASSWORD
            ),//Selecting columns want to query
            "$COLUMN_EMAIL=?",
            arrayOf(user.email), //where clause
            null,
            null,
            null
        )
        //if cursor has value then in user database there is user associated with this given email
        if (cursor !=null && cursor.moveToFirst() && cursor.count >0){
            val user1= UserModel(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            //Match both passwords check they are same or not
            if(user.password.equals(user1.password))
                return user1
        }
        //if user password does not matches or there is no record with that email then return @false
        return null
    }

    fun isEmailExists(email: String):Boolean{
        val db=this.writableDatabase
        val cursor=db.query(
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

    }
    /**
    //this method is for updating any existing information in the database table
    fun updateRow(row_id: String, name: String, email: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)

        val db = this.writableDatabase
        db.update(TABLE_USER, values, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun deleteRow(row_id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_USER, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun getAllRow(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_USER", null)
    }

    //method to check if user already exist
 fun checkUser(email: String):Boolean{
     // array of columns to fetch
     val columns = arrayOf(COLUMN_ID)
     val db = this.readableDatabase

     // selection criteria
     val selection = "$COLUMN_EMAIL = ?"

     // selection argument
     val selectionArgs = arrayOf(email)

     // query user table with condition
     val cursor = db.query(
         TABLE_USER, //Table to query
         columns,        //columns to return
         selection,      //columns for the WHERE clause
         selectionArgs,  //The values for the WHERE clause
         null,  //group the rows
         null,   //filter by row groups
         null)  //The sort order


     val cursorCount = cursor.count
     cursor.close()
     db.close()

     if (cursorCount > 0) {
         return true
     }

     return false
 }
*/
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
        const val COLUMN_USER_PASSWORD= "password"
    }
}



