package com.bitnoobwa.loanmeter.exceptions;

/**
 * Created by aparsh on 5/19/2015.
 */
public class EmptyTransactionListException extends Exception {
    public EmptyTransactionListException(String detailMessage) {
        super(detailMessage);
    }
}
