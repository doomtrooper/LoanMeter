package com.bitnoobwa.loanmeter.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bitnoobwa.loanmeter.interfaces.TransactionInterface;
import com.bitnoobwa.loanmeter.model.Person;
import com.bitnoobwa.loanmeter.model.Transaction;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = DatabaseDetails.DATABASE_VERSION;

    private static final String DATABASE_NAME = DatabaseDetails.DATABASE_NAME;

    public static final String TABLE_PERSON = DatabaseDetails.TABLE_Person;
    public static final String TABLE_TRANSACTION = DatabaseDetails.TABLE_Transaction;

    // Person Table Columns names
    public static String KEY_PERSON_ID = DatabaseDetails.KEY_Person_ID;
    public static String KEY_PERSON_NAME = DatabaseDetails.KEY_Person_NAME;
    public static String KEY_PERSON_IS_DELETED = DatabaseDetails.KEY_Person_Is_Deleted;
    public static String KEY_PERSON_COMMENTS = DatabaseDetails.KEY_Person_Comments;

    //Transaction table Column names
    public static String KEY_TRANSACTION_ID = DatabaseDetails.KEY_Transaction_ID;
    public static String KEY_TRANSACTION_PERSON_ID = DatabaseDetails.KEY_Transaction_Person_Id;
    public static String KEY_TRANSACTION_AMOUNT = DatabaseDetails.KEY_Transaction_Amount;
    public static String KEY_TRANSACTION_TIMESTAMP = DatabaseDetails.KEY_Transaction_TimeStamp;

    // Table Create Statements
    // Person table create statement
    private static final String CREATE_TABLE_PERSON = "CREATE TABLE "
            + TABLE_PERSON + "("
            + KEY_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PERSON_NAME + " TEXT UNIQUE,"
            + KEY_TRANSACTION_AMOUNT + " DOUBLE NOT NULL"
            + KEY_PERSON_IS_DELETED + " INTEGER DEFAULT 0," + KEY_PERSON_COMMENTS
            + " TEXT," + ")";

    // Transaction table create statement
    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION
            + "("
            + KEY_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TRANSACTION_PERSON_ID + " INTEGER,"
            + KEY_TRANSACTION_TIMESTAMP + " INTEGER DEFAULT CURRENT_TIMESTAMP"
            + " FOREIGN KEY(" + KEY_TRANSACTION_PERSON_ID + ") REFERENCES " + TABLE_PERSON + " (" + KEY_PERSON_ID + ")" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_PERSON);
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        // create new tables
        onCreate(db);
    }

}
