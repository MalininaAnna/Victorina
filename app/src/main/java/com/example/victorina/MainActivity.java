package com.example.victorina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button yesBtn;
    private Button noBtn;
    private Button showAnswer;
    private Button listAnswerBtn;
    private TextView listAnswer;
    private TextView textView;
    private StringBuilder stringBuilder = new StringBuilder();
    private Question[] questions = new Question[]{
            new Question(R.string.question0, false),
            new Question(R.string.question1, false),
            new Question(R.string.question2, false),
            new Question(R.string.question3, true),
            new Question(R.string.question4, false),
            new Question(R.string.question5, true),
            new Question(R.string.question6, false)
    };
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt("questionIndex");
        }

        yesBtn = findViewById(R.id.btnYes);
        noBtn = findViewById(R.id.btnNo);
        listAnswerBtn = findViewById(R.id.listAnswerBtn);
        textView = findViewById(R.id.textView);
        listAnswerBtn = findViewById(R.id.listAnswerBtn);
        showAnswer = findViewById(R.id.showAnswer);
        textView.setText(questions[questionIndex].getQuestionResId());
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions[questionIndex].isAnswerTrue()) {
                    Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                    setResult(questionIndex, true);
                } else {
                    Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    setResult(questionIndex, false);
                }

                if (questionIndex < (questions.length - 1)) {
                    questionIndex++;
                    textView.setText(questions[questionIndex].getQuestionResId());
                } else {
                    showResult();
                }
                //questionIndex = (questionIndex+1)%questions.length;
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions[questionIndex].isAnswerTrue()) {
                    Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    setResult(questionIndex, false);
                } else {
                    Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                    setResult(questionIndex, true);
                }
                if (questionIndex < (questions.length - 1)) {
                    questionIndex++;
                    textView.setText(questions[questionIndex].getQuestionResId());
                } else {
                    showResult();
                }
            }
        });
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
                intent.putExtra("answer", questions[questionIndex].isAnswerTrue());
                startActivity(intent);
            }
        });
        listAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinishActivity.class);
                startActivity(intent);
                showResult();
            }
        });
    }


    public void setResult(int num, boolean question) {
        stringBuilder.append("Вопрос №"+ num + "\n" + getString(questions[questionIndex].getQuestionResId()) + "\n" + ((question) ? "Верно" : "Неверно") + "\n");
    }

    public void showResult() {
        Intent intent = new Intent(MainActivity.this, FinishActivity.class);

        intent.putExtra("answerList", stringBuilder.toString());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("questionIndex", questionIndex);
    }
}
