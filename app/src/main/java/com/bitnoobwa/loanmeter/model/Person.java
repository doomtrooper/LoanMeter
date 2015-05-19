package com.bitnoobwa.loanmeter.model;

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

    public Person() {
    }

    public Person(String personName, Transaction transaction) {
        this.personName = personName;
        this.transaction = transaction;
        this.personId = Integer.parseInt(null);
        this.isDeleted = Integer.parseInt(null);
    }

    public Person(String personName, int isDeleted, Transaction transaction) {
        this.personName = personName;
        this.isDeleted = isDeleted;
        this.transaction = transaction;
        this.personId = Integer.parseInt(null);
    }

    public Person(int personId, String personName, int isDeleted,Transaction transaction) {
        this.personId = personId;
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

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public void insertIntoTransactionList(Transaction transaction){
        this.transactionList.add(transaction);
    }

}
