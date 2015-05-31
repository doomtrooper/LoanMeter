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
 * Created by razor on 30/5/15.
 */
public class UpdateTransactionDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface UpdateTransactionDialogListener{
        public void onPersistPositiveTransaction(DialogFragment dialog,String transactionAmount,int personId);
        public void onPersistNegativeTransaction(DialogFragment dialog,String transactionAmount,int personId);
    }

    UpdateTransactionDialogListener mListener;
    private View dialogView;
    private int personId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    // Overriding the Fragment.onAttach() method to instantiate the EnterPersonDetailsDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the Listener so we can send events to the host
            mListener = (UpdateTransactionDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateTransactionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle personArgs = getArguments();
        String personName = personArgs.getString("personName");
        setPersonId(personArgs.getInt("personId"));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_main_dialog_box, null);
        builder.setTitle("Add Transaction");
        EditText nameEditText = (EditText) dialogView.findViewById(R.id.enter_person_name);
        nameEditText.setText(personName);
        nameEditText.setEnabled(false);
        nameEditText.setFocusable(false);
        EditText commentEditText = (EditText) dialogView.findViewById(R.id.enter_comment);
        commentEditText.setEnabled(false);
        commentEditText.setFocusable(false);
        builder.setIcon(R.drawable.ic_stat_name);
        builder.setView(dialogView)
                .setPositiveButton("Add",new OnAddTransactionClickListener())
                .setNegativeButton("Subtract",new OnSubtractTransactionClickListener());
        return builder.create();
    }

    public class OnAddTransactionClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            try{
                String transactionAmount = ((EditText) dialogView.findViewById(R.id.enter_amount)).getText().toString();
                mListener.onPersistPositiveTransaction(UpdateTransactionDialogFragment.this,transactionAmount,getPersonId());
            }catch (NullPointerException exp){
                Log.v("exception",exp.getMessage());
            }
        }
    }
    public class OnSubtractTransactionClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                String transactionAmount = ((EditText) dialogView.findViewById(R.id.enter_amount)).getText().toString();
                mListener.onPersistNegativeTransaction(UpdateTransactionDialogFragment.this, transactionAmount,getPersonId());
            }catch (NullPointerException exp){
                Log.v("exception",exp.getMessage());
            }
        }
    }
}
