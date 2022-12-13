package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChatSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_search); //호출시 activity_search.xml을 띄움

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            // activity_search.xml에 있는 취소 버튼이 눌렸을 때
            @Override
            public void onClick(View view) {
                finish(); //finish()함수 호출 (finish()함수 : 창 종료)
            }
        });

        /**

        activity_search에 관한 코드 짜면 되는 곳

        **/

        /*
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar(); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
        */
    }

    //창 종료시 현재 창이 아래로 내려가는 애니메이션 추가
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup,R.anim.todown);
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