package com.bitnoobwa.loanmeter.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.bitnoobwa.loanmeter.interfaces.TransactionInterface;
import com.bitnoobwa.loanmeter.model.Person;
import com.bitnoobwa.loanmeter.model.Transaction;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public class EntryDataSource implements TransactionInterface {
    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;

    public EntryDataSource(Context context) {
        this.dbHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }
    @Override
    public void addTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_TRANSACTION_PERSON_ID,transaction.getPersonId());
        
    }

    @Override
    public ArrayList<Transaction> getPersonTransactionList(Person person) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getPersonTransactionList(int personID) {
        return null;
    }
}
