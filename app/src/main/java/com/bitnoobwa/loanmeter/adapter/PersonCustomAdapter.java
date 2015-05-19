package com.bitnoobwa.loanmeter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitnoobwa.loanmeter.R;
import com.bitnoobwa.loanmeter.exceptions.EmptyTransactionListException;
import com.bitnoobwa.loanmeter.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aparsh on 5/19/2015.
 */
public class PersonCustomAdapter extends ArrayAdapter<Person> {

    public PersonCustomAdapter(Context context, int resource, ArrayList<Person> personArrayList) {
        super(context, resource, personArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        //Log.d("position",String.valueOf(position));
        Person person=getItem(position);
        Double amount=0.0;
        try{
            amount=person.getTotalTransactionAmount();
        }catch (EmptyTransactionListException exp){
            amount=0.0;
        }
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_row_layout2, parent, false);
        // Lookup view for data population
        TextView personName=(TextView) convertView.findViewById(R.id.person_name);
        TextView personAmount=(TextView) convertView.findViewById(R.id.person_amount);
        // Populate the data into the template view using the data object
        personName.setText(person.getPersonName());
        personAmount.setText(amount.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
