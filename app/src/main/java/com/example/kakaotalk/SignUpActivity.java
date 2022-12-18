package com.example.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtPass, edtName, edtTell, edtBirth;
    private Button btnJoin;
    private ImageButton btnBack;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (EditText) findViewById(R.id.editText_email);
        edtPass = (EditText) findViewById(R.id.editText_passWord);
        edtName = (EditText) findViewById(R.id.editText_name);
        edtTell = (EditText) findViewById(R.id.editText_tellPhone);
        edtBirth = (EditText) findViewById(R.id.editText_birth);

        btnBack = (ImageButton) findViewById(R.id.signUp_backBtn);
        btnJoin = (Button) findViewById(R.id.btn_join);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String passWord = edtPass.getText().toString();
                String name = edtName.getText().toString();
                String tellPhone = edtTell.getText().toString();
                String birth = edtBirth.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Log.d("확인", String.valueOf(firebaseUser));
                            Person newUser = new Person(email, passWord, name, tellPhone, birth, "상태메시지", email.substring(0, email.indexOf("@")));
                            userDB.child(firebaseUser.getEmail().substring(0,firebaseUser.getEmail().indexOf("@"))).setValue(newUser);
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup, R.anim.todown);
    }
}