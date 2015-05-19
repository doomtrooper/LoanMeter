package com.bitnoobwa.loanmeter.interfaces;

import com.bitnoobwa.loanmeter.model.Person;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public interface PersonInterface {
    public void addPerson(Person person);
    public ArrayList<Person> allPersonList();
    public Person getPersonTransactionList(int personID);
    public Person getPersonTransactionList(Person person);
    //public void updatePersonName();

}
