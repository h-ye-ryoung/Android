package com.example.machumbub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.logging.Level;

//
import android.widget.TextView;
import android.widget.Button;
//
import java.util.HashMap;
//
import android.view.View;
import android.util.Log;
//
import android.app.AlertDialog;
import android.content.DialogInterface;
//
import android.os.Handler;

public class quiz1Activity extends AppCompatActivity {

    Button memo, levelsolve, resolve, community;

    TextView quiz1_main;
    Button quiz_button1;
    Button quiz_button2;

    String TAG = "quiz1Activity";

    HashMap[] problems = {
            new HashMap() {
                {
                    put("question", "'늘이고'와 '늘리고'\n" + "어떤 것이 맞는 말?");
                    put("answer", "수명을 늘리고 있습니다.");
                    put("example1", "수명을 늘이고 있습니다.");
                    put("example2", "수명을 늘리고 있습니다.");
                }
            },
            new HashMap() {
                {
                    put("question", "'달디달다'와 '다디달다'\n" + "어떤 것이 맞는 말?");
                    put("answer", "초콜릿이 다디달다.");
                    put("example1", "초콜릿이 달디달다.");
                    put("example2", "초콜릿이 다디달다.");
                }
            },
            new HashMap() {
                {
                    put("question", "'전셋집'과 '전세집'\n" + "어떤 것이 맞는 말?");
                    put("answer", "어렵게 전셋집을 구했다.");
                    put("example1", "어렵게 전세집을 구했다.");
                    put("example2", "어렵게 전셋집을 구했다.");
                }
            },
            new HashMap() {
                {
                    put("question", "'이따가'와 '있다가'\n" + "어떤 것이 맞는 말?");
                    put("answer", "제 이름 이따가 불러주세요.");
                    put("example1", "제 이름 있다가 불러주세요.");
                    put("example2", "제 이름 이따가 불러주세요.");
                }
            },
            new HashMap() {
                {
                    put("question", "'오두방정'과 '오도방정'\n" + "어떤 것이 맞는 말?");
                    put("answer", "오두방정을 떨다.");
                    put("example1", "오두방정을 떨다.");
                    put("example2", "오도방정을 떨다.");
                }
            },
            new HashMap() {
                {
                    put("question", "'정나미'과 '정내미'\n" + "어떤 것이 맞는 말?");
                    put("answer", "갑자기 정나미가 뚝 떨어진다.");
                    put("example1", "갑자기 정나미가 뚝 떨어진다.");
                    put("example2", "갑자기 정내미가 뚝 떨어진다.");
                }
            }
    };

    int problemNumber = 1;
    String question = "";
    String answer = "";
    String example1 = "";
    String example2 = "";

    int totalCorrect = 0;
    TextView totalCorrectTextView;
    TextView correctIncorrectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        memo = (Button) findViewById(R.id.memo);
        levelsolve = (Button) findViewById(R.id.levelsolve);
        resolve = (Button) findViewById(R.id.resolve);
        community = (Button) findViewById(R.id.community);


        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);
            }
        });

        levelsolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                LevlelSolveFragment levlelSolveFragment = new LevlelSolveFragment();
                transaction.replace(R.id.main_frame, levlelSolveFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                ResolveFragment resolveFragment = new ResolveFragment();
                transaction.replace(R.id.main_frame, resolveFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CommunityFragment communityFragment = new CommunityFragment();
                transaction.replace(R.id.main_frame, communityFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        quiz1_main = findViewById(R.id.quiz1_main);
        quiz_button1 = findViewById(R.id.quiz_button1);
        quiz_button2 = findViewById(R.id.quiz_button2);

        totalCorrectTextView = findViewById(R.id.totalCorrectTextView);
        correctIncorrectTextView = findViewById(R.id.correctIncorrectTextView);

        showProblem();

        totalCorrectTextView.setText("누적 점수: 0");
        correctIncorrectTextView.setText("Correct/Incorrect");
    }

    void showProblem() {



        question = (String)problems[problemNumber - 1].get("question");
        answer = (String)problems[problemNumber - 1].get("answer");
        example1 = (String)problems[problemNumber - 1].get("example1");
        example2 = (String)problems[problemNumber - 1].get("example2");

        quiz1_main.setText(question);
        quiz_button1.setText(example1);
        quiz_button2.setText(example2);
    }

    public void example1ButtonClicked(View v) {
        Log.d(TAG, "example1ButtonClicked");
        selectExample(example1);
    }

    void selectExample(String example) {
        Log.d(TAG, example);
        if (answer.equals(example)) {
            totalCorrect += 1;
            totalCorrectTextView.setText("누적 점수: " + Integer.toString(totalCorrect));
            correctIncorrectTextView.setText("맞았습니다");
        }
        else {
            correctIncorrectTextView.setText("틀렸습니다");
        }

        quiz_button1.setEnabled(false);
        quiz_button2.setEnabled(false);

        Handler h = new Handler() ;
        h.postDelayed(new Runnable() {
            public void run() {
                quiz_button1.setEnabled(true);
                quiz_button2.setEnabled(true);

                if (problemNumber < problems.length) {
                    problemNumber += 1;
                    showProblem();
                } else {
                    Log.d(TAG, "showGameOverBox");
                    showGameOverBox();
                }
            }}, 1000);
    }


    void showGameOverBox() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("게임 종료")
                .setMessage("최종 맞은 개수: " + totalCorrect + "\n 게임을 다시 하시겠습니까?")
                .setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goPrev();
                    }
                })
                .setPositiveButton("다시 하기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        replay();
                    }
                })
                .setCancelable(false) //true by default
                .create();
        alertDialog.show();
    }

    void goPrev() {
        Intent intent = new Intent(getApplicationContext(), MainLevelActivity.class);
        startActivity(intent);
    }

    void replay() {
        problemNumber = 1;
        totalCorrect = 0;
        //
        showProblem();
        //
        totalCorrectTextView.setText("Total Correct: 0");
        correctIncorrectTextView.setText("Correct/Incorrect");
    }

    public void example2ButtonClicked(View v) {
        Log.d(TAG, "example2ButtonClicked");
        selectExample(example2);
    }



}