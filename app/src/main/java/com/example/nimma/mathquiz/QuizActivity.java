package com.example.nimma.mathquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class QuizActivity extends AppCompatActivity implements NumberPadFragment.OnAnswerInput{

    QuestionFragment  frag_question;
    NumberPadFragment frag_num_pad;
    String strChosenOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Make sure we know the fragment instances
        frag_num_pad = (NumberPadFragment) getFragmentManager().findFragmentById(R.id.numberpad_fragment);

        //Retrieve data from the home activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            strChosenOperator = extras.getString("STR_OPERATOR_CHOSEN");
            frag_question = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);
            frag_question.SetChoseOperator(strChosenOperator);
        }

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
}
