package com.bitnoobwa.loanmeter.helper;

/**
 * Created by razor on 18/5/15.
 */
public interface DatabaseDetails {
    // All Static variables
    // Database Version
    public static int DATABASE_VERSION = 1;

    // Database Name
    public static String DATABASE_NAME = "loanMeter";

    // Contacts table name
    public static String TABLE_Person = "personDetails";
    public static String TABLE_Transaction= "transactionDetails";

    // Person Table Columns names
    public static String KEY_Person_ID = "personId";
    public static String KEY_Person_NAME = "personName";
    public static String KEY_Person_Is_Deleted = "personIsDeleted";
    public static String KEY_Person_Comments = "personComments";

    //Transaction table Column names
    public static String KEY_Transaction_ID = "transactionId";
    public static String KEY_Transaction_Person_Id = "personId";
    public static String KEY_Transaction_Amount = "transactionAmount";
    public static String KEY_Transaction_TimeStamp = "transactionTimeStamp";

    //queries
    public static String AllPerson_Query = "SELECT "+DatabaseHandler.KEY_PERSON_ID+" as _id "+"from "
            + DatabaseHandler.TABLE_PERSON + " WHERE "
            + DatabaseHandler.KEY_PERSON_IS_DELETED + " =0";

    public static String deletePersonQuery = "UPDATE "
            +DatabaseHandler.TABLE_PERSON+" SET "
            +DatabaseHandler.KEY_PERSON_IS_DELETED+" =1 WHERE "
            +DatabaseHandler.KEY_PERSON_ID+" = ?";
}
