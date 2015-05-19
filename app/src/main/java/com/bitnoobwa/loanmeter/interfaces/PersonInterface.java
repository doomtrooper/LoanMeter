package com.bitnoobwa.loanmeter.interfaces;

import com.bitnoobwa.loanmeter.exceptions.PersonAlreadyExistsException;
import com.bitnoobwa.loanmeter.exceptions.PersonNotUniqueException;
import com.bitnoobwa.loanmeter.model.Person;

import java.util.ArrayList;

/**
 * Created by razor on 18/5/15.
 */
public interface PersonInterface {
    public void addPerson(Person person) throws PersonAlreadyExistsException,PersonNotUniqueException;
    public ArrayList<Person> allPersonList();
    public Person getPerson(int personID) throws PersonNotUniqueException;
    public Person getPerson(Person person) throws PersonNotUniqueException;
    public Person getPerson(String personName) throws PersonNotUniqueException;
    public int getPersonId(String personName) throws PersonNotUniqueException;
    //public void updatePersonName();

}
