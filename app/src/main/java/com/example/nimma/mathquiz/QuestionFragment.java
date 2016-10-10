package com.example.nimma.mathquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class QuestionFragment extends Fragment {

    //Common variables for quiz operation
    public static final String NUM_OPERAND_1 = "NUM_OPERAND_1";
    public static final String NUM_OPERAND_2 = "NUM_OPERAND_2";
    public static final String STR_LATEST_ANSWER = "STR_LATEST_ANSWER";
    public static final String BOOL_INIT_ANSWER_NOT_CHANGED = "BOOL_INIT_ANSWER_NOT_CHANGED";
    public static final String STR_OPERATOR_CHOSEN = "STR_OPERATOR_CHOSEN";
    public static final String STR_ANSWER_SO_FAR = "STR_OPERATOR_CHOSEN";
    public static final String NUM_SCORE_SO_FAR = "NUM_SCORE_SO_FAR";
    public static final String NUM_QUESTIONS_SO_FAR = "NUM_QUESTIONS_SO_FAR";
    public static final String STR_TIME_REMAINING = "TIME_REMAINING";
    public static final int TOTAL_QUESTIONS = 10;


    //These are changeables
    public static String strLatestAnswer;
    public static boolean bInitialAnswerNotChanged = true;
    public static int numQuestionsSoFar = 0;
    public static int numScore = 0;
    public static long QUESTION_TIMER =  5000;
    public static long startTime = 0;
    public static long endTime = 0;
    public static long TIME_REMAINING = 5000;

    //Rest of the variables
    int numOperand1;
    int numOperand2;
    int numCorrectAnswer;
    public static String strOperand1, strOperand2, strAnswerSoFar;
    public static String strAnsParsed;
    public static String strChosenOperator;

    //Hail mary timer
    public MyTimerTask my_timer_task;
    public Timer timer;
    public boolean bQuizOver = false;
    public CountDownTimer cdTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_question, container, false);


        startTime = System.currentTimeMillis();

        //Handle orientation changes
        //Restore values from prior orientation
        if (savedInstanceState != null){
            numOperand1 = savedInstanceState.getInt(NUM_OPERAND_1);
            numOperand2 = savedInstanceState.getInt(NUM_OPERAND_2);

            strAnswerSoFar = savedInstanceState.getString(STR_LATEST_ANSWER);
            bInitialAnswerNotChanged = savedInstanceState.getBoolean(BOOL_INIT_ANSWER_NOT_CHANGED);
            strChosenOperator = savedInstanceState.getString(STR_OPERATOR_CHOSEN);

            numScore = savedInstanceState.getInt(NUM_SCORE_SO_FAR);
            numQuestionsSoFar = savedInstanceState.getInt(NUM_QUESTIONS_SO_FAR);

            TIME_REMAINING = savedInstanceState.getLong(STR_TIME_REMAINING);
        }
        else {
            //Generate random numbers
            Random rand = new Random();
            numOperand1 = rand.nextInt(5) + 5;
            numOperand2 = rand.nextInt(5) + 1;
            strAnswerSoFar = "=";
            numQuestionsSoFar = numQuestionsSoFar + 1;
            TIME_REMAINING = QUESTION_TIMER;

            if(numQuestionsSoFar >= TOTAL_QUESTIONS + 1){ //Zero based - ends here
                //Create the dialog for end of quiz
                final SummaryDialog sd = new SummaryDialog();
                final String text = "Your score is: " + numScore + " out of " + TOTAL_QUESTIONS;

                sd.UpdateTextDialog(getActivity(), text);
                sd.setCancelable(false);
                sd.show(getActivity().getFragmentManager(), "SummaryDialog");

                numQuestionsSoFar = 0; //Remember to reset the questions so far, to restart the game from home
                numScore = 0; //Same for score
                //Cancel the timer?
                if(my_timer_task!= null){
                    my_timer_task.cancel();
                }
                bQuizOver = true;
            }

        }

        //Set the string operands
        strOperand1 = String.valueOf(numOperand1);
        strOperand2 = String.valueOf(numOperand2);

        //Set text views for question
        TextView tv1 = (TextView) rootview.findViewById(R.id.operand1_textview);
        tv1.setText(strOperand1);
        TextView tv2 = (TextView) rootview.findViewById(R.id.operand2_textview);
        tv2.setText(strOperand2);


        //Set text view for answer (to handle orientation changes too)
        TextView tv3 = (TextView) rootview.findViewById(R.id.answer_textview);
        if(!bInitialAnswerNotChanged) {
            tv3.setText(strAnswerSoFar);
        }

        //Set question number in action bar
        String title = "MathQuiz  -  Q. " + numQuestionsSoFar + " out of 10";
        AppCompatActivity ab = (AppCompatActivity) getActivity();
        ab.getSupportActionBar().setTitle(title);


        //Schedule a new question for five seconds
        //ScheduleNewQuestion();
        if(!bQuizOver) {
            //ScheduleNewTimer();

            cdTimer = new CountDownTimer(TIME_REMAINING, 1000) {

                public void onTick(long millisUntilFinished) {
                    //Countdown logic here
                }

                public void onFinish() {
                    SetImageForAnswer("wrong");
                    MoveToNextQuestion();
                }
            }.start();
        }

        return rootview;
    }

/*
    private void ScheduleNewQuestion() {
        //Change the question in 5 seconds
        new Timer().schedule(new TimerTask(){
            public void run() {
                if(getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            SetImageForAnswer("wrong");
                            MoveToNextQuestion();
                        }
                    });
                }
            }
        }, TIME_REMAINING); //Time remaining for orientation changes
    }
*/
    private void ScheduleNewTimer(){

        if(bQuizOver) return;

        my_timer_task = new MyTimerTask();
        timer = new Timer();
        timer.schedule(my_timer_task, TIME_REMAINING);
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        SetImageForAnswer("wrong");
                        MoveToNextQuestion();
                    }
                });
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Handle state changes for orientation
        super.onSaveInstanceState(outState);
        outState.putInt(NUM_OPERAND_1, numOperand1);
        outState.putInt(NUM_OPERAND_2, numOperand2);
        outState.putString(STR_LATEST_ANSWER, strLatestAnswer);
        outState.putString(STR_ANSWER_SO_FAR, strAnswerSoFar);
        outState.putBoolean(BOOL_INIT_ANSWER_NOT_CHANGED, bInitialAnswerNotChanged);
        outState.putString(STR_OPERATOR_CHOSEN, strChosenOperator);
        outState.putInt(NUM_SCORE_SO_FAR, numScore);
        outState.putInt(NUM_QUESTIONS_SO_FAR, numQuestionsSoFar);

        endTime = System.currentTimeMillis();
        TIME_REMAINING = QUESTION_TIMER - (endTime - startTime);
        outState.putLong(STR_TIME_REMAINING, TIME_REMAINING);

        //Cancel timer again?
        if(cdTimer != null) {
            cdTimer.cancel();
        }
    }


    //7. (Contd from QuestionActivity and before that NumberPadFragment
    //Finally, use the data from NumberPadActivity to update the answer
    public void updateAnswer(String strInput) {
        TextView tv = (TextView) getView().findViewById(R.id.answer_textview);

        if(!strInput.equals("ENTER")) {
            //Get previous answer
            String strPrevText = tv.getText().toString();

            if (bInitialAnswerNotChanged) {
                //Update the initial answer by removing whitespaces
                strPrevText = "=";
                bInitialAnswerNotChanged = false;
            }

            //Do pre-processing for answer here before updating text
            String strNewText = strPrevText + strInput;
            tv.setText(strNewText);

            //Save answer in state for orientation changes
            strLatestAnswer = strNewText;

            strAnsParsed = strLatestAnswer.replaceAll("\\D+", "");
            if (Integer.parseInt(strAnsParsed) == numCorrectAnswer) {
                numScore = numScore + 1;
                strLatestAnswer = strAnswerSoFar = "=";
                //Stop the countdowntimer
                cdTimer.cancel();
                SetImageForAnswer("correct");
                MoveToNextQuestion();

            }
        }
        else{

            if(strAnsParsed != null && !strAnsParsed.trim().isEmpty()) {
                if (Integer.parseInt(strAnsParsed) != numCorrectAnswer) {
                    cdTimer.cancel();
                    SetImageForAnswer("wrong");
                    strLatestAnswer = strAnswerSoFar = "=";
                    MoveToNextQuestion();
                }
            }
            else{
                cdTimer.cancel();
                SetImageForAnswer("wrong");
                MoveToNextQuestion();
            }
        }

    }

    private void SetImageForAnswer(String answer) {
        ImageView iv = (ImageView) getView().findViewById(R.id.quiz_icon_placeholder);
        if(answer == "wrong") {
            iv.setImageResource(R.mipmap.wrong);
        }
        else if (answer == "correct"){
            iv.setImageResource(R.mipmap.correct);
        }
    }

    private void MoveToNextQuestion() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    //method to get operator from quizActivity
    public void SetChoseOperator(String strChOp){
        strChosenOperator = strChOp;
        if(strChosenOperator.equals("+")){
            numCorrectAnswer =  numOperand1 + numOperand2;
        }
        else if(strChosenOperator.equals("-")){
            numCorrectAnswer = numOperand1 - numOperand2;
        }
        else if(strChosenOperator.equals("*")){
            numCorrectAnswer = numOperand1 * numOperand2;
        }

        //Get the chosen operator and put it up on screen
        TextView tvOperator = (TextView) getActivity().findViewById(R.id.operator_textview);
        tvOperator.setText(strChosenOperator);

    }

}