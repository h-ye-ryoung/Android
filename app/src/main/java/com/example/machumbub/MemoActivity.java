package com.example.machumbub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

public class MemoActivity extends AppCompatActivity {
    private int REQUEST_TEST = 200;

    Button write_btn;
    PreferenceManager pref;
    RecyclerView recyclerView;
    MemoAdapter memoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        pref = new PreferenceManager();
        write_btn = findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteMemoActivity.class);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });

        //리사이클러뷰 세팅
        LinearLayoutManager linearLayoutManager;
        recyclerView = findViewById(R.id.memo_rv);//리사이클러뷰 findView
        linearLayoutManager = new LinearLayoutManager(MemoActivity.this, LinearLayoutManager.VERTICAL, false);

        memoAdapter = new MemoAdapter(MemoActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        recyclerView.setAdapter(memoAdapter);//adapter 세팅


        //쉐어드 모든 키 벨류 가져오기
        SharedPreferences prefb =getSharedPreferences("memo_contain", MODE_PRIVATE);
        Collection<?> col_val =  prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();

        while(it_val.hasNext() && it_key.hasNext()) {
            String key = (String) it_key.next();
            String value = (String) it_val.next();
            try {
                JSONObject jsonObject = new JSONObject(value);
                String title = (String) jsonObject.getString("title");
                String content = (String) jsonObject.getString("content");
                memoAdapter.addItem(new MemoItem(key, title, content));
            } catch (JSONException e) {
                Log.d("MainActivity","JSONObject : "+e);
            }

            memoAdapter.notifyDataSetChanged();

        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        getSupportActionBar().setTitle("메인 화면"); //툴바 제목
        //pref.clear(MainActivity.this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { //백 키 눌렀을 때 동작

                //액티비티 이동
                Intent intent = new Intent(getApplicationContext(), MainLevelActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                String get_date = data.getStringExtra("date");
                String get_title = data.getStringExtra("title");
                String get_content = data.getStringExtra("content");

                memoAdapter.addItem(new MemoItem(get_date,get_title,get_content));
                memoAdapter.notifyDataSetChanged();
                Toast.makeText(MemoActivity.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();

            } else {   // RESULT_CANCEL
                Toast.makeText(MemoActivity.this, "저장 되지 않음", Toast.LENGTH_SHORT).show();
            }

        }
    }
}