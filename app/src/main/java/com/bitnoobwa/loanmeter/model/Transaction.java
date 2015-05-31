package com.bitnoobwa.loanmeter.model;

/**
 * Created by razor on 18/5/15.
 */
public class Transaction {
    private int transactionId;
    private int personId;
    private int timeStamp;
    private double amount;

    public Transaction() {
    }

    public Transaction(int timeStamp, double amount) {
        this.timeStamp = timeStamp;
        this.amount = amount;
    }
    public Transaction(int timeStamp, double amount, int personId){
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.personId = personId;
    }
    public Transaction(int transactionId, double amount, int timeStamp, int personId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.personId = personId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", personId=" + personId +
                ", timeStamp=" + timeStamp +
                ", amount=" + amount +
                '}';
    }
}
