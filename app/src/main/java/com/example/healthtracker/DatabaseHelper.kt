package com.example.healthtracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//extend to SQLiteOpenHelper to manage database creation and version management
class DatabaseHelper(context: Context?):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create User table SQL query
    private val CREATE_TABLE_USER = (" CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    //create Sleep table SQL query
    private val CREATE_TABLE_SLEEP = (" CREATE TABLE " + TABLE_SLEEP + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + HOURS_SLEPT + " INTEGER,"
            + DATE + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //create Fitness table SQL query
    private val CREATE_TABLE_FITNESS = (" CREATE TABLE " + TABLE_FITNESS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + CALORIES_BURNED + " INTEGER,"
            + DATE_TIME + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //create Food table SQL query
    private val CREATE_TABLE_FOOD = (" CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + CALORIES_GAINED + " INTEGER,"
            + DATE_TIME + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //drop User table sql query
    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_USER"

    //drop Sleep table sql query
    private val DROP_SLEEP_TABLE = " DROP TABLE IF EXISTS $TABLE_SLEEP"

    //drop Fitness table sql query
    private val DROP_FITNESS_TABLE = " DROP TABLE IF EXISTS $TABLE_FITNESS"

    //drop Food table sql query
    private val DROP_FOOD_TABLE = " DROP TABLE IF EXISTS $TABLE_FOOD"

    //this function is called once ( the first time of the execution), it creates the database tables using SQL query
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_SLEEP)
        db.execSQL(CREATE_TABLE_FITNESS)
        db.execSQL(CREATE_TABLE_FOOD)
    }

    //this method will be called when we change the database version.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //drop table if already exist
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_SLEEP_TABLE)
        db.execSQL(DROP_FITNESS_TABLE)
        db.execSQL(DROP_FOOD_TABLE)
        //create table again
        onCreate(db)
    }

    //this method is for insert a data into database User table
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

    //this method is for insert a data into database Sleep table
    fun insertSleepData(userID: Int, hoursSlept: Int, date: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(HOURS_SLEPT, hoursSlept)
        contentValues.put(DATE, date)

        //inserting row into database
        db.insert(TABLE_SLEEP, null, contentValues)
        db.close()
    }

    //this method is for insert a data into database Fitness table
    fun insertFitnessData(userID: Int, caloriesBurned: Int, dateTime: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(CALORIES_BURNED, caloriesBurned)
        contentValues.put(DATE_TIME, dateTime)

        //inserting row into database
        db.insert(TABLE_FITNESS, null, contentValues)
        db.close()
    }

    //this method is for insert a data into database Food table
    fun insertFoodData(userID: Int, caloriesGained: Int, dateTime: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(CALORIES_GAINED, caloriesGained)
        contentValues.put(DATE_TIME, dateTime)

        //inserting row into database
        db.insert(TABLE_FOOD, null, contentValues)
        db.close()
    }

    //check if user with given email and password exists
    fun userExists(email: String, password: String):Boolean {
        val db=writableDatabase
        val query= "select * from users where email = '$email' and password = '$password' "
        val cursor = db.rawQuery(query,null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.moveToFirst()
        currentUserID = (cursor.getString(cursor.getColumnIndex(COLUMN_ID))).toInt()
        cursor.close()
        return true
    }

    //check if hours slept for specified user this day exists
    fun hoursSleptExists(userID: Int, date: String):Boolean {
        val db=writableDatabase
        val query= "select * from sleep where userId = '$userID' and date = '$date' "
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
        db.update(TABLE_USER, values, "$COLUMN_ID =?", arrayOf(users.id))
        db.close()
    }

    fun deleteUserData(users:UserModel){
        val db = this.writableDatabase
        db.delete(TABLE_USER, "COLUMN_ID=?", arrayOf(users.id))
        db.close()
    }

    companion object {
        //database version
        const val DATABASE_VERSION = 1
        //database name
        const val DATABASE_NAME = "user.db"
        //table names
        const val TABLE_USER = "users"
        const val TABLE_SLEEP = "sleep"
        const val TABLE_FITNESS = "fitness"
        const val TABLE_FOOD = "fitness"
        //ID column @primary key
        const val COLUMN_ID = "id"
        //column names for user details
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
        //column names for sleep details
        const val COLUMN_USER_ID = "userId"
        const val HOURS_SLEPT = "hoursSlept"
        const val DATE = "date"
        //column names for fitness details
        const val CALORIES_BURNED = "caloriesBurned"
        const val DATE_TIME = "dateTime"
        //column names for food details
        const val CALORIES_GAINED = "caloriesGained"

        var currentUserID:Int = 0
    }
}
