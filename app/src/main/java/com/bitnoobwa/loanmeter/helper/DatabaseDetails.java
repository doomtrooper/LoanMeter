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
}
