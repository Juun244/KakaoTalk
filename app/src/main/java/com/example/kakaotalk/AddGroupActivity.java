package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddGroupActivity extends AppCompatActivity {

    public ArrayList<Person> friends;
    AddGroupAdapter adapter = new AddGroupAdapter();
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_group);

        EditText Text1 = (EditText)findViewById(R.id.add_friend_id);

        ImageButton backBtn = (ImageButton) findViewById(R.id.addGroup_backBtn);

        recyclerView = (RecyclerView) findViewById(R.id.addFriendProfile_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        friends = (ArrayList<Person>) intent.getSerializableExtra("friends");
        //Toast.makeText(this, String.valueOf(friends.size()), Toast.LENGTH_SHORT).show();
        for(Person person : friends){
            adapter.addItem(person);
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Text1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        Text1.setText(null);

                        break;
                }
                return false;
            }
        });

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