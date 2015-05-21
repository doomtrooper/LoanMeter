package com.bitnoobwa.loanmeter.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bitnoobwa.loanmeter.R;
import com.bitnoobwa.loanmeter.exceptions.EmptyTransactionListException;
import com.bitnoobwa.loanmeter.exceptions.PersonNotUniqueException;
import com.bitnoobwa.loanmeter.helper.DatabaseHandler;
import com.bitnoobwa.loanmeter.helper.EntryDataSource;
import com.bitnoobwa.loanmeter.model.Person;

/**
 * Created by razor on 21/5/15.
 */
public class PersonCursorAdapter extends CursorAdapter{


    public PersonCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);
        Log.v("cursor",String.valueOf(cursor.getCount()));
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v("inside newView",String.valueOf(cursor.getCount()));
        return LayoutInflater.from(context).inflate(R.layout.activity_main_row_layout2, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView personName=(TextView) view.findViewById(R.id.person_name);
        TextView personAmount=(TextView) view.findViewById(R.id.person_amount);
        Person person = new Person();
        // Extract properties from cursor
        //String body = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_PERSON_NAME));
        EntryDataSource dataSource = new EntryDataSource(context);
        try {
            person = dataSource.getPerson(cursor.getInt(cursor.getColumnIndex("_id")));
        } catch (PersonNotUniqueException e) {
            Log.v("exception",e.getMessage());
        }
        Double amount=0.0;
        try{
            amount=person.getTotalTransactionAmount();
        }catch (EmptyTransactionListException exp){
            Log.v("EmptyTransactionListException",exp.getMessage());
            amount=0.0;
        }
        Log.v("bindView",String.valueOf(amount)+" "+String.valueOf(person.getPersonName()));
        // Populate fields with extracted properties
        personName.setText(person.getPersonName());
        personAmount.setText(String.valueOf(amount));
    }
}
