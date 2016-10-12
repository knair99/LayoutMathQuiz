package com.example.nimma.mathquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class QuizActivity extends AppCompatActivity implements NumberPadFragment.OnAnswerInput{

    QuestionFragment  frag_question;
    NumberPadFragment frag_num_pad;
    String strChosenOperator;
    public static boolean bDisableOrientationChanges = false;

    public static long lastClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //bDisableOrientationChanges = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init progressBar
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(5); //For five seconds


        //Make sure we know the fragment instances
        frag_num_pad = (NumberPadFragment) getFragmentManager().findFragmentById(R.id.numberpad_fragment);
        frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);

        getSupportActionBar().setTitle("Question " + frag_question.numQuestionsSoFar + " out of 10");

        //Retrieve data from the home activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            strChosenOperator = extras.getString("STR_OPERATOR_CHOSEN");
            frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
            frag_question.SetChoseOperator(strChosenOperator);
        }

     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home: {
                //Pause timer as we wait for user input
                frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
                frag_question.cdTimer.cancel();

                //bDisableOrientationChanges = true;
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

                ExitDialog exit_dialog = new ExitDialog();
                exit_dialog.setCancelable(false);
                exit_dialog.show(getFragmentManager(), "ExitDialog");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Handle any button clicked from the number pad here and pass it to the fragment
    public void btnOnClickNum(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        frag_num_pad.fragmentBtnOnClickNum(view, btnText);
    }


    //6. (Contd from NumberPadFragment)
    //Implement the interface from NumberPadFragment
    @Override
    public void sendInput(String strInput) {
        //Get the question Fragment
        frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);

        //Call the method in there with the new data
        frag_question.updateAnswer(strInput);
    }


    @Override
    public void onBackPressed() {
        //Pause timer as we wait for user input
        frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
        frag_question.cdTimer.cancel();

        //bDisableOrientationChanges = true;
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        ExitDialog exit_dialog = new ExitDialog();
        exit_dialog.setCancelable(false);
        exit_dialog.show(getFragmentManager(), "ExitDialog");

    }

    @Override
    protected void onPause() {
        frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
        if(frag_question.cdTimer != null) {
            frag_question.cdTimer.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
        frag_question.bStartingTimeFromPause = true;
        if(!frag_question.bQuizOver) {
            frag_question.CreateAndStartTimer();
        }
        super.onResume();
    }
}

