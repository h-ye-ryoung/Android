package com.example.machumbub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.machumbub.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteMemoActivity extends AppCompatActivity {

    PreferenceManager pref;
    Button back_btn;
    Button save_btn;
    EditText title;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_memo);

        //툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        getSupportActionBar().setTitle("메모 목록"); //툴바 제목


        pref = new PreferenceManager();
        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);
        // editText 할당
        title = findViewById(R.id.memo_title_edit);
        content = findViewById(R.id.memo_content_edit);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 저장 버튼을 눌러
                // 작성한 editText를 저장
                String edit_title = title.getText().toString();
                String edit_content = content.getText().toString();
                // String 값을 JSONObject로 변환하여 사용할 수 있도록 메모의 제목과 타이틀을 JSON 형식로 저장
                String save_form = "{\"title\":\""+edit_title+"\",\"content\":\""+edit_content+"\"}";

                // key값이 겹치지 않도록 현재 시간으로 부여
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate).toString();

                Log.d("WriteActivity","제목 : "+edit_title+", 내용 : "+edit_content+", 현재시간 : "+getTime);
                //PreferenceManager 클래스에서 저장에 관한 메소드를 관리
                pref.setString(getApplication(),getTime,save_form);


                // Intent로 값을 MainActivity에 전달
                Intent intent = new Intent();
                intent.putExtra("date",getTime);
                intent.putExtra("title",edit_title);
                intent.putExtra("content",edit_content);
                setResult(RESULT_OK, intent);
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
