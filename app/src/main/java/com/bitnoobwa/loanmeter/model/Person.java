package com.bitnoobwa.loanmeter.model;

import com.bitnoobwa.loanmeter.exceptions.EmptyTransactionListException;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public class Person {
    private int personId;
    private String personName;
    private int isDeleted;
    private Transaction transaction;
    private ArrayList<Transaction> transactionList;
    private String personComments;

    public String getPersonComments() {
        return personComments;
    }

    public void setPersonComments(String personComments) {
        this.personComments = personComments;
    }

    public Person() {
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Person(String personName, Transaction transaction,String personComments) {
        this.personName = personName;
        this.transaction = transaction;
        this.personComments = personComments;
        this.personId = Integer.parseInt(null);
        this.isDeleted = Integer.parseInt(null);
    }

    public Person(String personName, int isDeleted, Transaction transaction,String personComments) {
        this.personName = personName;
        this.personComments = personComments;
        this.isDeleted = isDeleted;
        this.transaction = transaction;
        this.personId = Integer.parseInt(null);
    }

    public Person(int personId, String personName, int isDeleted,Transaction transaction,String personComments) {
        this.personId = personId;
        this.personComments = personComments;
        this.personName = personName;
        this.isDeleted = isDeleted;
        this.transaction = transaction;
        this.transactionList=new ArrayList<Transaction>();
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    public String getPersonName() {
        return personName;

    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setTransactionList(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public double getTotalTransactionAmount() throws EmptyTransactionListException {
        double amount=0.0;
        ArrayList<Transaction> transactionArrayList=getTransactionList();
        if(transactionArrayList==null || transactionArrayList.isEmpty())
            throw new EmptyTransactionListException("The transaction List is empty or NULL");
        for(Transaction transaction:getTransactionList())
            amount+=transaction.getAmount();
        return amount;
    }

    @Override
    public String toString() {
        return "Person{" +
                "isDeleted=" + isDeleted +
                ", personId=" + personId +
                ", personName='" + personName + '\'' +
                ", transaction=" + transaction +
                ", personComments='" + personComments + '\'' +
                ", transactionList=" + transactionList +
                '}';
    }

    public void insertIntoTransactionList(Transaction transaction){
        this.transactionList.add(transaction);
    }

}
