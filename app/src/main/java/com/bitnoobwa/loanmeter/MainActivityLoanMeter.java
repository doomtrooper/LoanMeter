package com.bitnoobwa.loanmeter;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bitnoobwa.loanmeter.adapter.PersonCursorAdapter;
import com.bitnoobwa.loanmeter.adapter.PersonCustomAdapter;
import com.bitnoobwa.loanmeter.dialog.EnterPersonDetailsDialogFragment;
import com.bitnoobwa.loanmeter.exceptions.PersonAlreadyExistsException;
import com.bitnoobwa.loanmeter.exceptions.PersonNotUniqueException;
import com.bitnoobwa.loanmeter.helper.DatabaseDetails;
import com.bitnoobwa.loanmeter.helper.DatabaseHandler;
import com.bitnoobwa.loanmeter.helper.EntryDataSource;
import com.bitnoobwa.loanmeter.model.Person;

import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivityLoanMeter extends AppCompatActivity implements EnterPersonDetailsDialogFragment.EnterPersonDetailsDialogListener {

    private EntryDataSource dataSource;
    private PersonCursorAdapter personCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Below is a SQLiteOpenHelper class connecting to SQLite
        dataSource = new EntryDataSource(getApplicationContext());

        // Get access to the underlying writeable database
        dataSource.read();
        Cursor personCursor = dataSource.getDatabase().rawQuery(DatabaseDetails.AllPerson_Query, null);
        setContentView(R.layout.activity_main_activity_loan_meter_listview);
        personCursorAdapter = new PersonCursorAdapter(this,personCursor);
        ListView listView = (ListView) findViewById(R.id.lvPerson);
        listView.setAdapter(personCursorAdapter);
        dataSource.close();
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
            createNewPersonDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewPersonDialog() {
        DialogFragment dialogFragment = new EnterPersonDetailsDialogFragment();
        dialogFragment.show(getFragmentManager(),"EnterPersonDetailsDialogFragment");
    }

    //Dialog Box Interface implemented Methods.

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the EnterPersonDetailsDialogFragment.EnterPersonDetailsDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog,String[] dialogValues) {
        // User touched the dialog's positive button
        //dialog.getText(R.id.enter_person_name);
        try {
            //Log.v("dialog values",dialogValues[0]+dialogValues[1]+dialogValues[2]);
            dataSource.addPerson(createNewPerson(dialogValues));
            dataSource.read();
            Cursor newPersonCursor = dataSource.getDatabase().rawQuery(DatabaseDetails.AllPerson_Query, null);
            personCursorAdapter.swapCursor(newPersonCursor);
            dataSource.close();
            //Log.v("Person added", dataSource.getPerson("b").toString());
        }catch (PersonAlreadyExistsException alreadyExistsExp){
            Log.v("exception", alreadyExistsExp.getMessage());
        }catch (PersonNotUniqueException notUniqueExp){
            Log.v("exception", notUniqueExp.getMessage());
        }
        dialog.dismiss();
        //PersonCustomAdapter adapter = (PersonCustomAdapter) getListAdapter();
    }

    public Person createNewPerson(String[] values){
        Log.v("dialog values 2",values[0]+values[1]+values[2]);
        Person person = new Person();
        person.setPersonName(values[0]);
        person.getTransaction().setAmount(Double.parseDouble(values[1]));
        person.getTransaction().setTimeStamp((int)System.currentTimeMillis()); //Unix Time STamp
        person.setPersonComments(values[2]);
        Log.v("Person details",person.toString());
        return person;
    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        dialog.dismiss();
    }
}
