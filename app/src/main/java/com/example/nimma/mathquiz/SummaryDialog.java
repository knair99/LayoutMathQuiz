package com.example.nimma.mathquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
//import android.support.v4.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class SummaryDialog extends DialogFragment {

    public AlertDialog.Builder builder;
    public String strTextToUpdate;
    Activity myActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Display a dialog with the score
        builder = new AlertDialog.Builder(getActivity()).setCancelable(false);

        //Set the score
        builder.setTitle("Done!");

        builder.setMessage(strTextToUpdate);

        // Add the buttons
        builder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                myActivity.finish();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    public void UpdateTextDialog(Activity activity, final String text){
        strTextToUpdate = text;
        myActivity = activity;
    }


}
