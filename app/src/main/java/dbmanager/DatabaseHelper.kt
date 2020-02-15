package dbmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import userPackage.User

abstract class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //create table SQL query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")


    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }
    companion object{
    //database name
    private val DATABASE_NAME = "UserManager.db"

    //database version
    private val DATABASE_VERSION ="1"

    //user table name
    private val TABLE_USER = "user"

    //user table columns names
    private val COLUMN_USER_ID ="user_id"
    private val COLUMN_USER_NAME = "user_name"
    private val COLUMN_USER_EMAIL = "user_email"
    private val COLUMN_USER_PASSWORD = "user_password"
}
}



