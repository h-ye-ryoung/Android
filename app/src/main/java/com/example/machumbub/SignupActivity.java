package com.example.machumbub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//툴바 임포트
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail, mEtPwd, mEtPwd_ide, mEtNickname;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Machumbub");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtPwd_ide = findViewById(R.id.et_pwd_ide);
        mEtNickname = findViewById(R.id.et_nickname);
        mBtnRegister = findViewById(R.id.startb);

        //툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        getSupportActionBar().setTitle("로그인"); //툴바 제목

        //공부하기 버튼
        Button Startbutton = (Button) findViewById(R.id.startb);
        Startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strPwd_ide = mEtPwd_ide.getText().toString();
                String strNickname = mEtNickname.getText().toString();
                boolean check = strPwd.equals(strPwd_ide);


                if (check == true) {
                    // Firebase Auth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 현재 회원가입이 된 유저를 가지고 옴
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd); // Firebase에서 패스워드를 가져올 수 없기 때문에 위에서 생성된것 가져온다.
                                account.setNickname(strNickname);

                                // Database 하위 개념으로 추가함
                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainLevelActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if (check == false) {
                    Toast.makeText(SignupActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //툴바 설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { //백 키 눌렀을 때 동작

                //액티비티 이동
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}