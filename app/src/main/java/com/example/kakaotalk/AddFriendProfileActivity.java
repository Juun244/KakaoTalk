package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddFriendProfileActivity extends AppCompatActivity {

    TextView View1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_friend);

        View1 = (TextView)findViewById(R.id.profile_text);

        ImageButton backBtn = (ImageButton) findViewById(R.id.search_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // [예제] 친구 아이디 입력에서 아이디 값 받아오기
        // 받을 데이터: 프로필 이미지, 아이디
        Bundle intent = getIntent().getExtras();
        View1.setText(intent.getString("TEXT"));


    }

    //창 종료시 현재 창이 아래로 내려가는 애니메이션 추가
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup, R.anim.todown);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home: //툴바 뒤로가기버튼 눌렸을 때 동작
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }
}