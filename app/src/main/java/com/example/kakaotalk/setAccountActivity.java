package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class setAccountActivity extends AppCompatActivity {
    private DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlayout1);
        TextView email = (TextView) findViewById(R.id.email);
        TextView tel = (TextView) findViewById(R.id.tele);
        TextView pass = (TextView) findViewById(R.id.PW);

        Intent intent = getIntent();
        String id = intent.getStringExtra("이메일");

        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            Person item;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    try {
                        item = dataSnapshot.getValue(Person.class);
                    }catch (Exception e){
                        //Toast.makeText(setAccountActivity.this, "test", Toast.LENGTH_SHORT).show();
                    }
                    if(item.getEmail().equals(id)){
                        email.setText(item.getEmail());
                        tel.setText(item.getTellPhone());
                        pass.setText(item.getPassword());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton backBtn = (ImageButton) findViewById(R.id.setAccount_backBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //finish()함수 호출 (finish()함수 : 창 종료)
            }
        });
    }
}
