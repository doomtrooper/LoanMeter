package com.bitnoobwa.loanmeter.interfaces;

import com.bitnoobwa.loanmeter.model.Person;
import com.bitnoobwa.loanmeter.model.Transaction;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public interface TransactionInterface {

    public void addTransaction(Transaction transaction);
    public ArrayList<Transaction> getPersonTransactionList(Person person);
    public ArrayList<Transaction> getPersonTransactionList(int personID);
}
