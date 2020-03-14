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
            + COLUMN_USER_AGE + " INTEGER," + COLUMN_DR_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    //create Sleep table SQL query
    private val CREATE_TABLE_SLEEP = (" CREATE TABLE " + TABLE_SLEEP + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_HOURS_SLEPT + " INTEGER,"
            + COLUMN_DATE + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //create Appointment table SQL query
    private val CREATE_TABLE_APPOINTMENT = (" CREATE TABLE " + TABLE_APPOINTMENT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_TIME + " TEXT,"
            + COLUMN_ROOM + " TEXT,"
            + COLUMN_DOCTOR_NAME + " TEXT,"
            + COLUMN_SELECTED + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //drop User table sql query
    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_USER"

    //drop Sleep table sql query
    private val DROP_SLEEP_TABLE = " DROP TABLE IF EXISTS $TABLE_SLEEP"

    //drop Appointment table sql query
    private val DROP_APPOINTMENT_TABLE = " DROP TABLE IF EXISTS $TABLE_APPOINTMENT"

    //this function is called once ( the first time of the execution), it creates the database tables using SQL query
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_SLEEP)
        db.execSQL(CREATE_TABLE_APPOINTMENT)
    }

    //this method will be called when we change the database version.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //drop table if already exist
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_SLEEP_TABLE)
        db.execSQL(DROP_APPOINTMENT_TABLE)
        //create table again
        onCreate(db)
    }

    //this method is for insert a data into database User table
    fun insertUserData(name: String, age: String , drName: String ,email: String, password: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_USER_AGE, age)
        contentValues.put(COLUMN_DR_NAME,drName)
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
        contentValues.put(COLUMN_HOURS_SLEPT, hoursSlept)
        contentValues.put(COLUMN_DATE, date)

        //inserting row into database
        db.insert(TABLE_SLEEP, null, contentValues)
        db.close()
    }

    //this method is for insert a data into database Appointment table
    fun insertAppointmentData(userID: Int, description: String, date: String, time: String, room: String, doctorName: String, selected: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(COLUMN_DESCRIPTION, description)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_TIME, time)
        contentValues.put(COLUMN_ROOM, room)
        contentValues.put(COLUMN_DOCTOR_NAME, doctorName)
        contentValues.put(COLUMN_SELECTED, selected)

        //inserting row into database
        db.insert(TABLE_APPOINTMENT, null, contentValues)
        db.close()
    }

    //get appointments from database
    fun getAppointments(userID: Int,selected: Int):MutableList<Appointment>? {

        val db=writableDatabase
        val query= "select * from appointment where userId = '$userID' and selected = '$selected' "
        val cursor = db.rawQuery(query,null)
        var appointments: MutableList<Appointment>? = null

        if(cursor != null) {
            var id:Int
            var userID:Int
            var description:String
            var date:String
            var time:String
            var room:String
            var doctorName:String
            var selected:Int
            var appointment:Appointment

            for (i in 1..cursor.count) {
                if (i==1) {
                    cursor.moveToFirst()
                    id = (cursor.getString(cursor.getColumnIndex(COLUMN_ID))).toInt()
                    userID = (cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))).toInt()
                    description = (cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                    date = (cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))
                    time = (cursor.getString(cursor.getColumnIndex(COLUMN_TIME)))
                    room = (cursor.getString(cursor.getColumnIndex(COLUMN_ROOM)))
                    doctorName = (cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR_NAME)))
                    selected = (cursor.getString(cursor.getColumnIndex(COLUMN_SELECTED))).toInt()
                    appointment = Appointment(id,userID,description,date,time,room,doctorName,selected)
                    appointments = mutableListOf(appointment)
                } else {
                    cursor.moveToNext()
                    id = (cursor.getString(cursor.getColumnIndex(COLUMN_ID))).toInt()
                    userID = (cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))).toInt()
                    description = (cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)))
                    date = (cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))
                    time = (cursor.getString(cursor.getColumnIndex(COLUMN_TIME)))
                    room = (cursor.getString(cursor.getColumnIndex(COLUMN_ROOM)))
                    doctorName = (cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR_NAME)))
                    selected = (cursor.getString(cursor.getColumnIndex(COLUMN_SELECTED))).toInt()
                    appointment = Appointment(id,userID,description,date,time,room,doctorName,selected)
                    appointments?.add(appointment)
                }
            }
        }
        cursor.close()
        return appointments
    }

    //update appointments table
    fun updateAppointment(userID: Int, appointmentID:Int) {
        val db=writableDatabase
        db.execSQL("UPDATE appointment SET selected = 1 , userId = $userID WHERE selected = 0 AND id = $appointmentID;")

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
        values.put(COLUMN_USER_AGE,users.age)
        values.put(COLUMN_DR_NAME,users.drName)
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
        const val TABLE_APPOINTMENT = "appointment"

        //ID column @primary key
        const val COLUMN_ID = "id"

        //column names for user details
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
        const val COLUMN_USER_AGE = "age"
        const val COLUMN_DR_NAME = "drName"

        //column names for sleep details
        const val COLUMN_USER_ID = "userId"
        const val COLUMN_HOURS_SLEPT = "hoursSlept"
        const val COLUMN_DATE = "date"

        //column names for appointment details
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TIME = "time"
        const val COLUMN_ROOM = "room"
        const val COLUMN_DOCTOR_NAME = "doctorName"
        const val COLUMN_SELECTED = "selected"

        var currentUserID:Int = 0
    }
}
