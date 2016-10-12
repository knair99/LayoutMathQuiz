package com.example.nimma.mathquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ExitDialog extends DialogFragment {

    public AlertDialog.Builder builder;
    public static String strTextToUpdate;
    Activity myActivity;
    public QuestionFragment frag_question;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Display a dialog with the score
        builder = new AlertDialog.Builder(getActivity()).setCancelable(false);

        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Exit Quiz?");
        builder.setMessage("Are you sure you want to exit this Quiz?");

        frag_question = (QuestionFragment) myActivity.getFragmentManager().findFragmentById(R.id.question_fragment);

        frag_question.bDontStartTimer = true;

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Drop all data
                        frag_question.strLatestAnswer = "";
                        frag_question.bInitialAnswerNotChanged = true;
                        frag_question.numQuestionsSoFar = 0;
                        frag_question.numScore = 0;
                        frag_question.QUESTION_TIMER =  5000;
                        frag_question.startTime = 0;
                        frag_question.endTime = 0;
                        frag_question.TIME_REMAINING = 5000;

                        getActivity().finish(); //? bug here
                    }

                });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        frag_question.bDontStartTimer = false;
                        frag_question.CreateAndStartTimer();
                    }
                });

        return builder.create();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.myActivity = activity;
    }

}
