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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.logging.Level;


public class MainLevelActivity extends AppCompatActivity {

    Button memo, levelsolve, resolve, community;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_level);

        //레벨1버튼
        /*Button level1b = (Button) findViewById(R.id.level1);
        level1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), level1Activity.class);
                startActivity(intent);
            }
        });*/

        //하단탭
        memo = (Button) findViewById(R.id.memo);
        levelsolve = (Button) findViewById(R.id.levelsolve);
        resolve = (Button) findViewById(R.id.resolve);
        community = (Button) findViewById(R.id.community);

        LevlelSolveFragment levlelSolveFragment = new LevlelSolveFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, levlelSolveFragment).commit();

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

        //툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        getSupportActionBar().setTitle("메인 화면"); //툴바 제목

    }

    //툴바 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { //백 키 눌렀을 때 동작

                //액티비티 이동
                Intent intent = new Intent(getApplicationContext(), RealSignupActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //마이페이지 설정

}