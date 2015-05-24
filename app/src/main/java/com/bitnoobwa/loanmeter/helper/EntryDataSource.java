package com.bitnoobwa.loanmeter.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bitnoobwa.loanmeter.exceptions.PersonAlreadyExistsException;
import com.bitnoobwa.loanmeter.exceptions.PersonNotUniqueException;
import com.bitnoobwa.loanmeter.interfaces.PersonInterface;
import com.bitnoobwa.loanmeter.interfaces.TransactionInterface;
import com.bitnoobwa.loanmeter.model.Person;
import com.bitnoobwa.loanmeter.model.Transaction;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public class EntryDataSource implements TransactionInterface, PersonInterface {
    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;

    public EntryDataSource(Context context) {
        this.dbHandler = new DatabaseHandler(context);
    }

    public void write() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    public void read() throws SQLException {
        database = dbHandler.getReadableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    //Transaction Interface Implementation
    @Override
    public void addTransaction(Transaction transaction) {
        //Log.v("Transaction add",transaction.toString());
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_TRANSACTION_PERSON_ID, transaction.getPersonId());
        values.put(DatabaseHandler.KEY_TRANSACTION_AMOUNT, transaction.getAmount());
        values.put(DatabaseHandler.KEY_TRANSACTION_TIMESTAMP, transaction.getTimeStamp());
        write();
        database.insert(DatabaseHandler.TABLE_TRANSACTION, null, values);
        close();
    }

    @Override
    public ArrayList<Transaction> getPersonTransactionList(Person person) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getPersonTransactionList(int personID) {
        //Log.v("personIdgetTransaction",String.valueOf(personID));
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DatabaseHandler.TABLE_TRANSACTION+" WHERE "
                +DatabaseHandler.KEY_TRANSACTION_PERSON_ID+" = "+personID;
        //Log.v("query",query);
        read();
        Cursor cursor = database.rawQuery(query,null);
        //Log.v("cursor",cursor.toString());
        if(cursor.getCount()<=0) return null;
        cursor.moveToFirst();
        do {
            Transaction transaction = new Transaction();
            //Log.v("cursordetails",String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_PERSON_ID)))+String.valueOf(cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_AMOUNT))));
            transaction.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_PERSON_ID)));
            transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_AMOUNT)));
            transaction.setTransactionId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_ID)));
            transaction.setTimeStamp(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_TIMESTAMP)));
            //Log.v("gettransaction",transaction.toString());
            transactionArrayList.add(transaction);
        }while (cursor.moveToNext());
        close();
        return transactionArrayList;
    }


    //Person Interface Implementation
    @Override
    public void addPerson(Person person) throws PersonAlreadyExistsException, PersonNotUniqueException {
        if (getPerson(person.getPersonName()) == null) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.KEY_PERSON_NAME, person.getPersonName());
            values.put(DatabaseHandler.KEY_PERSON_COMMENTS, person.getPersonComments());
            write();
            database.insert(DatabaseHandler.TABLE_PERSON, null, values);
            close();
            person.getTransaction().setPersonId(getPersonId(person.getPersonName()));
            Log.v("Transaction addPerson",person.getTransaction().toString());
            addTransaction(person.getTransaction());
        } else throw new PersonAlreadyExistsException("Person to be added already Exists");
    }

    @Override
    public ArrayList<Person> allPersonList() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE "
                + DatabaseHandler.KEY_PERSON_IS_DELETED + " =0";
        read();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount()<=0) return null;
        cursor.moveToFirst();
        do {
            Person person = new Person();
            person.setPersonName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_NAME)));
            person.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID)));
            person.setPersonComments(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_COMMENTS)));
            Log.v("comments",person.getPersonComments());
            person.setIsDeleted(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_IS_DELETED)));
            //person.setIsDeleted(0);
            person.setTransaction(null);
            person.setTransactionList(getPersonTransactionList(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID))));
            personArrayList.add(person);
        }while(cursor.moveToNext());
        close();
        return personArrayList;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public DatabaseHandler getDbHandler() {
        return dbHandler;
    }

    @Override
    public Person getPerson(int personID) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON
                + " WHERE " + DatabaseHandler.KEY_PERSON_ID + " = " + "'" + personID
                + "' AND " + DatabaseHandler.KEY_PERSON_IS_DELETED + " =0";
        read();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 1)
            throw new PersonNotUniqueException("NON Unique Person FOUND!!!!");
        else if (cursor.getCount() == 0) return null;
        Person person = new Person();
        person.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID)));
        person.setIsDeleted(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_IS_DELETED)));
        person.setPersonName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_NAME)));
        person.setPersonComments(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_COMMENTS)));
        person.setTransaction(null);
        person.setTransactionList(getPersonTransactionList(person.getPersonId()));
        close();
        return person;
    }

    @Override
    public Person getPerson(Person tempPerson) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE "
                + DatabaseHandler.KEY_PERSON_ID + " = " + "'" + tempPerson.getPersonId()
                + "' AND "+ DatabaseHandler.KEY_PERSON_IS_DELETED + "=0";
        read();
        Cursor cursor = database.rawQuery(query, null);
        close();
        cursor.moveToFirst();
        if (cursor.getCount() > 1)
            throw new PersonNotUniqueException("NON Unique Person FOUND!!!!");
        else if (cursor.getCount() == 0) return null;
        Person person = new Person();
        person.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID)));
        person.setIsDeleted(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_IS_DELETED)));
        person.setPersonName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_NAME)));
        person.setPersonComments(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_COMMENTS)));
        person.setTransaction(null);
        person.setTransactionList(getPersonTransactionList(person.getPersonId()));
        return person;
    }

    public Person getPerson(String personName) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE "
                + DatabaseHandler.KEY_PERSON_NAME + " = " + "'" + personName
                + "' AND "+ DatabaseHandler.KEY_PERSON_IS_DELETED + "=0";
        read();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 1){
            close();
            throw new PersonNotUniqueException("NON Unique Person FOUND!!!!");
        }
        else if (cursor.getCount() == 0) return null;
        Person person = new Person();
        person.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID)));
        person.setIsDeleted(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_IS_DELETED)));
        person.setPersonName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_NAME)));
        person.setPersonComments(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_COMMENTS)));
        person.setTransaction(null);
        person.setTransactionList(getPersonTransactionList(person.getPersonId()));
        close();
        return person;
    }

    @Override
    public int getPersonId(String personName) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE "
                + DatabaseHandler.KEY_PERSON_NAME + " = " + "'" + personName
                + "' AND "+ DatabaseHandler.KEY_PERSON_IS_DELETED + "=0";
        read();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 1)
            throw new PersonNotUniqueException("NON Unique Person FOUND!!!!");
        int personId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID));
        Log.v("person id",String.valueOf(personId));
        close();
        return personId;
    }

}
