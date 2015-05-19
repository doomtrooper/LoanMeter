package com.bitnoobwa.loanmeter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bitnoobwa.loanmeter.adapter.PersonCustomAdapter;
import com.bitnoobwa.loanmeter.helper.EntryDataSource;
import com.bitnoobwa.loanmeter.model.Person;

import java.util.ArrayList;


public class MainActivityLoanMeter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EntryDataSource dataSource = new EntryDataSource(getApplicationContext());
        ArrayList<Person> personArrayList = new ArrayList<Person>();
        personArrayList = dataSource.allPersonList();

        if(personArrayList.isEmpty())
            setContentView(R.layout.activity_main_activity_loan_meter_no_entry);
        else {
            setContentView(R.layout.activity_main_activity_loan_meter_listview);
            populateEntriesListView(personArrayList);
        }
        //setContentView(R.layout.activity_main_activity_loan_meter);

    }

    private void populateEntriesListView(ArrayList<Person> personArrayList) {
        PersonCustomAdapter personCustomAdapter = new PersonCustomAdapter(this,R.layout.activity_main_row_layout2,personArrayList);
        final ListView listView = (ListView) findViewById(R.id.person);
        listView.setAdapter(personCustomAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_loan_meter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_add_person){

        }
        return super.onOptionsItemSelected(item);
    }
}
