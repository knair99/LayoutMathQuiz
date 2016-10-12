package com.example.nimma.mathquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
    }

    public void imgAddQuizHandler(View view) {
        startQuiz("+");
    }

    public void imgSubQuizHandler(View view) {
        startQuiz("-");
    }

    public void imgMulQuizHandler(View view) {
        startQuiz("*");
    }
    private void startQuiz(String value) {
        Intent quizIntent = new Intent(this, QuizActivity.class);
        quizIntent.putExtra("STR_OPERATOR_CHOSEN", value);
        startActivity(quizIntent);
    }


}
