package com.example.geoquiz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.geoquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String EXTRA_ANSWER ="com.example.geoquiz.answer";
    private static final String EXTRA_ANSWER_SHOW="com.example.geoquiz.answer_shown";
    private boolean mIsCheater;


    private ActivityMainBinding binding;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        if (savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        
        //int question = mQuestionsBank[mCurrentIndex].getTextResId();
        //binding.questionTextView.setText(question);
        updateQuestion();

        binding.btnPrevious.setEnabled(false);

        binding.btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, R.string.correctToast, Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        binding.btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, R.string.incorrectToast, Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionsBank.length;
                //int question = mQuestionsBank[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                updateQuestion();

                if (mCurrentIndex+1 == mQuestionsBank.length){
                    binding.btnNext.setEnabled(false);
                }

                if (mCurrentIndex != 0){
                    binding.btnPrevious.setEnabled(true);
                }

            }
        });

        binding.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex-1) % mQuestionsBank.length;
                updateQuestion();

                if (mCurrentIndex != 0){
                    binding.btnPrevious.setEnabled(true);
                } else{
                    binding.btnNext.setEnabled(true);
                    binding.btnPrevious.setEnabled(false);
                }

                if (mCurrentIndex+1 != mQuestionsBank.length){
                    binding.btnNext.setEnabled(true);
                }
            }
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            mIsCheater = result.getData().getBooleanExtra(EXTRA_ANSWER_SHOW,false);
                        }
                    }
                }
        );


        binding.btnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,cheatActivity.class);
                i.putExtra(EXTRA_ANSWER,mQuestionsBank[mCurrentIndex].isAnswerTrue());
                //startActivity(i);
                resultLauncher.launch(i);
            }
        });


        Log.d("Main Activity", "Testing");
    }

    private void updateQuestion(){
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        binding.questionTextView.setText(question);
        mIsCheater = false;
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        Log.d("checkAnswer","Click");
        if (mIsCheater){
            messageResId=R.string.judgement_toast;
        }
        else if (userPressedTrue== answerIsTrue){
            messageResId = R.string.correctToast;
        }
        else{
            messageResId=R.string.incorrectToast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", "On Start");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "On Resume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "On Pause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","On Stop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","On Destroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle","On Restart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }
}

