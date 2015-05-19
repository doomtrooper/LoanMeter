package com.bitnoobwa.loanmeter.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DatabaseHandler.TABLE_TRANSACTION+" WHERE "+DatabaseHandler.KEY_TRANSACTION_PERSON_ID+" = "+personID;
        read();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            Transaction transaction = new Transaction();
            transaction.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_PERSON_ID)));
            transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_AMOUNT)));
            transaction.setTransactionId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_ID)));
            transaction.setTimeStamp(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_TRANSACTION_TIMESTAMP)));
            transactionArrayList.add(transaction);
        }
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
        } else throw new PersonAlreadyExistsException("Person to be added already Exists");
    }

    @Override
    public ArrayList<Person> allPersonList() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE " + DatabaseHandler.KEY_PERSON_IS_DELETED + " !=0";
        read();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            Person person = new Person();
            person.setPersonName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_NAME)));
            person.setPersonId(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID)));
            person.setPersonComments(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_COMMENTS)));
            person.setIsDeleted(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_IS_DELETED)));
            //person.setIsDeleted(0);
            person.setTransaction(null);
            person.setTransactionList(getPersonTransactionList(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID))));
            personArrayList.add(person);
        }
        close();
        return personArrayList;
    }

    @Override
    public Person getPerson(int personID) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE " + DatabaseHandler.KEY_PERSON_ID + " = " + "'" + personID + "' AND " + DatabaseHandler.KEY_PERSON_IS_DELETED + " !=0";
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

    @Override
    public Person getPerson(Person tempPerson) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE " + DatabaseHandler.KEY_PERSON_ID + " = " + "'" + tempPerson.getPersonId() + "'";
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
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE " + DatabaseHandler.KEY_PERSON_NAME + " = " + "'" + personName + "'";
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

    @Override
    public int getPersonId(String personName) throws PersonNotUniqueException {
        String query = "SELECT * from " + DatabaseHandler.TABLE_PERSON + " WHERE " + DatabaseHandler.KEY_PERSON_NAME + " = " + "'" + personName + "'";
        read();
        Cursor cursor = database.rawQuery(query, null);
        close();
        cursor.moveToFirst();
        if (cursor.getCount() != 1)
            throw new PersonNotUniqueException("NON Unique Person FOUND!!!!");
        return cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_PERSON_ID));
    }

}
