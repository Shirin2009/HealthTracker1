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

    //this method is for insert a data into database table
    fun insertUserData(name: String, email: String, password: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_USER_PASSWORD, password)

        db.insert(TABLE_USER, null, contentValues)
        db.close()
    }

    /**
     * fun userPresents(id: String, name: String, email: String, password: String):Boolean{
    val db=writableDatabase
    val query= "select * from user where id= $id and name= $name and email = $email and password= $password"
    val cursor = db.rawQuery(query,null)
    if(cursor.count <= 0){
    cursor.close()
    return false
    }
    cursor.close()
    return true
    }
     * */
    //check if user exist
    fun userPresents(email: String, password: String):Boolean{
        val db=writableDatabase
        val query= "select * from users where email = '$email' and password= '$password' "
        val cursor = db.rawQuery(query,null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    //alternative function fro checking if the user already exist
   /** fun userPresent(id: String, name: String, email: String, password: String):Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id)
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_EMAIL, email)
        contentValues.put(COLUMN_USER_PASSWORD, password)
        db.update(TABLE_USER, contentValues, "ID=?", arrayOf(id))
        return true
    }
    */
    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USER, "ID=?", arrayOf(id))
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




