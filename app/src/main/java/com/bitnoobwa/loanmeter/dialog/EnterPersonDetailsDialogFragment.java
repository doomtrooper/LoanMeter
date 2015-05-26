package com.bitnoobwa.loanmeter.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bitnoobwa.loanmeter.R;

/**
 * Created by razor on 5/20/2015.
 */
public class EnterPersonDetailsDialogFragment extends DialogFragment {


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface EnterPersonDetailsDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,String[] dialogValues);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    EnterPersonDetailsDialogListener mListener;

    // Overriding the Fragment.onAttach() method to instantiate the EnterPersonDetailsDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EnterPersonDetailsDialogListener so we can send events to the host
            mListener = (EnterPersonDetailsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement EnterPersonDetailsDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.activity_main_dialog_box, null);
        builder.setIcon(R.drawable.ic_user);
        builder.setTitle(R.string.createPersonTitle);
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        //EditText dialogValues[] = new EditText[3];
                        String[] dialogValues = new String[3];
                        dialogValues[0] = ((EditText) dialogView.findViewById(R.id.enter_person_name)).getText().toString();
                        dialogValues[1] = ((EditText) dialogView.findViewById(R.id.enter_amount)).getText().toString();
                        dialogValues[2] = ((EditText) dialogView.findViewById(R.id.enter_comment)).getText().toString();
                        //Log.v("dialog values",dialogValues[0]+dialogValues[1]+dialogValues[2]);
                        mListener.onDialogPositiveClick(EnterPersonDetailsDialogFragment.this,dialogValues);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(EnterPersonDetailsDialogFragment.this);
                    }
                });
        return builder.create();
    }

}
