package com.example.healthtracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//extend to SQLiteOpenHelper to manage database creation and version management
class DatabaseHelper(context: Context?):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create User table SQL query
    private val CREATE_TABLE_USER = (" CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_USER_AGE + " TEXT," + COLUMN_DR_NAME + " TEXT,"
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
            + ACTIVITY_TYPE + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //create Food table SQL query
    private val CREATE_TABLE_FOOD = (" CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + CALORIES_GAINED + " INTEGER,"
            + DATE_TIME + " TEXT,"
            + FOOD_TYPE + " TEXT,"
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

    //create Cardiovascular table SQL query
    private val CREATE_TABLE_CARDIOVASCULAR = (" CREATE TABLE " + TABLE_CARDIOVASCULAR + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_DATETIME  + " TEXT DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_HEART_RATE + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID +"))")

    //drop User table sql query
    private val DROP_USER_TABLE = " DROP TABLE IF EXISTS $TABLE_USER"

    //drop Sleep table sql query
    private val DROP_SLEEP_TABLE = " DROP TABLE IF EXISTS $TABLE_SLEEP"

    //drop Fitness table sql query
    private val DROP_FITNESS_TABLE = " DROP TABLE IF EXISTS $TABLE_FITNESS"

    //drop Food table sql query
    private val DROP_FOOD_TABLE = " DROP TABLE IF EXISTS $TABLE_FOOD"

    //drop Appointment table sql query
    private val DROP_APPOINTMENT_TABLE = " DROP TABLE IF EXISTS $TABLE_APPOINTMENT"

    //drop Cardiovascular table sql query
    private val DROP_CARDIOVASCULAR_TABLE = " DROP TABLE IF EXISTS $TABLE_CARDIOVASCULAR"

    //this function is called once ( the first time of the execution), it creates the database tables using SQL query
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USER)
        db.execSQL(CREATE_TABLE_SLEEP)
        db.execSQL(CREATE_TABLE_FITNESS)
        db.execSQL(CREATE_TABLE_FOOD)
        db.execSQL(CREATE_TABLE_APPOINTMENT)
        db.execSQL(CREATE_TABLE_CARDIOVASCULAR)
    }

    //this method will be called when we change the database version.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //drop table if already exist
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_SLEEP_TABLE)
        db.execSQL(DROP_FITNESS_TABLE)
        db.execSQL(DROP_FOOD_TABLE)
        db.execSQL(DROP_APPOINTMENT_TABLE)
        db.execSQL(DROP_CARDIOVASCULAR_TABLE)
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
        contentValues.put(HOURS_SLEPT, hoursSlept)
        contentValues.put(DATE, date)

        //inserting row into database
        db.insert(TABLE_SLEEP, null, contentValues)
        db.close()
    }

    //this method is for insert a data into database Cardiovascular table
    fun insertCardiovascularData(userID: Int, heartRate: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(COLUMN_HEART_RATE, heartRate)

        //inserting row into database
        db.insert(TABLE_CARDIOVASCULAR, null, contentValues)
        db.close()
    }

    //this method is for insert a data into database Cardiovascular table
    fun insertCardiovascularData(userID: Int, dateTime: String, heartRate: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(COLUMN_DATETIME, dateTime)
        contentValues.put(COLUMN_HEART_RATE, heartRate)

        //inserting row into database
        db.insert(TABLE_CARDIOVASCULAR, null, contentValues)
        db.close()
    }

    fun displaySleep(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_SLEEP", null)
    }

    fun showSleepData(userID: Int, date: String):Int{
        val db=writableDatabase
        val query= "select * from sleep where dateTime = '$date' and userId = '$userID'"
        val cursor = db.rawQuery(query,null)
        var hourSlept = 0
        if(cursor.moveToFirst()){
            do{
                hourSlept += cursor.getInt(cursor.getColumnIndex(HOURS_SLEPT))
            }while(cursor.moveToNext())
        }
        else{
            hourSlept = -1
        }
        cursor.close()
        return hourSlept
    }

    fun checkSleepData(dateTime: String):Boolean{
        val db=writableDatabase
        val query= "select * from sleep where dateTime = '$dateTime'"
        val cursor = db.rawQuery(query,null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }

        return true
    }

    //this method is for insert a data into database Fitness table
    fun insertFitnessData(userID: Int, caloriesBurned: Int, dateTime: String, activityType: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(CALORIES_BURNED, caloriesBurned)
        contentValues.put(DATE_TIME, dateTime)
        contentValues.put(ACTIVITY_TYPE, activityType)

        //inserting row into database
        db.insert(TABLE_FITNESS, null, contentValues)
        db.close()
    }

    fun showFitnessData(userID: Int, dateTime: String, activityType: String):Int{
        val db=writableDatabase
        val query= "select * from fitness where dateTime = '$dateTime' and userId = '$userID' and activityType = '$activityType'"
        val cursor = db.rawQuery(query,null)
        var calsBurned = 0
        if(cursor.moveToFirst()){
            do{
                calsBurned += cursor.getInt(cursor.getColumnIndex(CALORIES_BURNED))
            }while(cursor.moveToNext())
        }
        else{
            calsBurned = -1
        }
        cursor.close()

        return calsBurned
    }


    //this method is for insert a data into database Food table
    fun insertFoodData(userID: Int, caloriesGained: Int, dateTime: String, foodType: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_USER_ID, userID)
        contentValues.put(CALORIES_GAINED, caloriesGained)
        contentValues.put(DATE_TIME, dateTime)
        contentValues.put(FOOD_TYPE, foodType)

        //inserting row into database
        db.insert(TABLE_FOOD, null, contentValues)
        db.close()
    }

    fun showFoodData(userID: Int, dateTime: String, foodType: String):Int{
        val db=writableDatabase
        val query= "select * from food where dateTime = '$dateTime' and userId = '$userID' and foodType = '$foodType'"
        val cursor = db.rawQuery(query,null)
        var calsGained = 0
        if(cursor.moveToFirst()){
            do{
                calsGained += cursor.getInt(cursor.getColumnIndex(CALORIES_GAINED))
            }while(cursor.moveToNext())
        }
        else{
            calsGained = -1
        }
        cursor.close()

        return calsGained
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

    //get today's average heart rate of a user
    fun todaysAverageHeartRate(userID: Int):Int {
        val db=writableDatabase
        val query= "select * from cardiovascular where userId = '$userID' and dateTime between datetime('now', 'start of day') AND datetime('now', 'localtime')"
        val cursor = db.rawQuery(query,null)
        var heartRate = 0
        var list: MutableList<Int>? = null

        if(cursor != null) {
            for (i in 1..cursor.count) {
                if (i==1) {
                    cursor.moveToFirst()
                    list = mutableListOf((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                } else {
                    cursor.moveToNext()
                    list?.add((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                }
            }
        }
        if (list != null) {
            heartRate = list.average().toInt()
        }
        cursor.close()
        return heartRate
    }

    //get weekly average heart rate of a user(last 7 days from current date)
    fun weeklyAverageHeartRate(userID: Int):Int {
        val db=writableDatabase
        val query= "select * from cardiovascular where userId = '$userID' and dateTime between datetime('now', '-7 days') AND datetime('now', 'localtime')"
        val cursor = db.rawQuery(query,null)
        var heartRate = 0
        var list: MutableList<Int>? = null

        if(cursor != null) {
            for (i in 1..cursor.count) {
                if (i==1) {
                    cursor.moveToFirst()
                    list = mutableListOf((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                } else {
                    cursor.moveToNext()
                    list?.add((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                }
            }
        }
        if (list != null) {
            heartRate = list.average().toInt()
        }
        cursor.close()
        return heartRate
    }

    //get monthly average heart rate of a user(last 1 month from current date)
    fun monthlyAverageHeartRate(userID: Int):Int {
        val db=writableDatabase
        val query= "select * from cardiovascular where userId = '$userID' and dateTime between datetime('now', '-1 months') AND datetime('now', 'localtime')"
        val cursor = db.rawQuery(query,null)
        var heartRate = 0
        var list: MutableList<Int>? = null

        if(cursor != null) {
            for (i in 1..cursor.count) {
                if (i==1) {
                    cursor.moveToFirst()
                    list = mutableListOf((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                } else {
                    cursor.moveToNext()
                    list?.add((cursor.getString(cursor.getColumnIndex(COLUMN_HEART_RATE))).toInt())
                }
            }
        }
        if (list != null) {
            heartRate = list.average().toInt()
        }
        cursor.close()
        return heartRate
    }

    //update appointments table
    fun updateAppointment(userID: Int, appointmentID:Int) {
        val db=writableDatabase
        db.execSQL("UPDATE appointment SET selected = 1 , userId = $userID WHERE selected = 0 AND id = $appointmentID;")
    }

    companion object {
        //database version
        const val DATABASE_VERSION = 3

        //database name
        const val DATABASE_NAME = "user.db"

        //table names
        const val TABLE_USER = "users"
        const val TABLE_SLEEP = "sleep"
        const val TABLE_FITNESS = "fitness"
        const val TABLE_FOOD = "food"
        const val TABLE_APPOINTMENT = "appointment"
        const val TABLE_CARDIOVASCULAR = "cardiovascular"

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
        const val HOURS_SLEPT = "hoursSlept"
        const val DATE = "date"

        //column names for fitness details
        const val CALORIES_BURNED = "caloriesBurned"
        const val DATE_TIME = "dateTime"
        const val ACTIVITY_TYPE = "activityType"

        //column names for food details
        const val CALORIES_GAINED = "caloriesGained"
        const val FOOD_TYPE = "foodType"

        //column names for appointment details
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TIME = "time"
        const val COLUMN_ROOM = "room"
        const val COLUMN_DOCTOR_NAME = "doctorName"
        const val COLUMN_SELECTED = "selected"
        const val COLUMN_DATE ="date"

        //column names for cardiovascular details
        const val COLUMN_HEART_RATE = "heartRate"
        const val COLUMN_DATETIME = "dateTime"

        var currentUserID:Int = 0
    }
}
