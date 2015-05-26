package com.bitnoobwa.loanmeter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bitnoobwa.loanmeter.R;

/**
 * Created by razor on 26/5/15.
 */
public class PersonOptionsDialogFragment extends DialogFragment{

    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle personArgs = getArguments();
        setPersonId(personArgs.getInt("personId"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(personArgs.getString("personName"))
                .setItems(R.array.person_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }
}
