package com.example.machumbub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {
    PreferenceManager pref;
    TextView view_title;
    TextView view_content;
    Button back_list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        getSupportActionBar().setTitle("메모 목록"); //툴바 제목

        pref = new PreferenceManager();
        view_title = findViewById(R.id.view_title);
        view_content = findViewById(R.id.view_content);
        back_list_btn = findViewById(R.id.back_list_btn);

        // 인텐트로 리사이클러뷰 목록 하나의 키값을 받는다
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        String value = pref.getString(getApplication(),key);
        try {
            JSONObject jsonObject = new JSONObject(value);
            String title = (String) jsonObject.getString("title");
            String content = (String) jsonObject.getString("content");
            view_title.setText(title);
            view_content.setText(content);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        back_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //툴바 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { //백 키 눌렀을 때 동작

                //액티비티 이동
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
