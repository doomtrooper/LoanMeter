package com.bitnoobwa.loanmeter.dialog;

import android.app.Activity;
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

    public interface PersonOptionsDialogListener{
        public void onDeleteListener(DialogFragment dialogFragment,int personId,String personName);
    }

    PersonOptionsDialogListener mListener;

    // Overriding the Fragment.onAttach() method to instantiate the PersonOptionsDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the PersonOptionsDialogListener so we can send events to the host
            mListener = (PersonOptionsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PersonOptionsDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle personArgs = getArguments();
        final String personName = personArgs.getString("personName");
        setPersonId(personArgs.getInt("personId"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(personName)
                .setItems(R.array.person_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which){
                            case 1:
                                mListener.onDeleteListener(PersonOptionsDialogFragment.this,getPersonId(),personName);
                        }
                    }
                });
        return builder.create();
    }
}
