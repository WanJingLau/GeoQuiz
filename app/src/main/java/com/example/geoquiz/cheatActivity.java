package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.geoquiz.databinding.ActivityCheatBinding;

public class cheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER ="com.example.geoquiz.answer";
    private static final String EXTRA_ANSWER_SHOW="com.example.geoquiz.answer_shown";

    private boolean mAnswer;

    private ActivityCheatBinding cheatBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cheatBinding = ActivityCheatBinding.inflate(getLayoutInflater());
        View v = cheatBinding.getRoot();
        setContentView(v);
        //setContentView(R.layout.activity_cheat);
        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER,false);
        cheatBinding.btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswer)
                    cheatBinding.answerTextView.setText(R.string.true_button);
                else
                    cheatBinding.answerTextView.setText(R.string.false_button);
                setAnswerShownResult(true);
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data= new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW,isAnswerShown);
        setResult(RESULT_OK,data);
    }

}