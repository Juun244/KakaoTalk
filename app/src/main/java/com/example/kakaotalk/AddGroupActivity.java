package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AddGroupActivity extends AppCompatActivity {

    public ArrayList<Person> friends;
    AddGroupAdapter adapter = new AddGroupAdapter();
    public RecyclerView recyclerView;
    Button add;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_group);

        EditText Text1 = (EditText)findViewById(R.id.add_friend_id);
        add = (Button) findViewById(R.id.group_add_button);
        ImageButton backBtn = (ImageButton) findViewById(R.id.addGroup_backBtn);

        recyclerView = (RecyclerView) findViewById(R.id.addFriendProfile_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        friends = (ArrayList<Person>) intent.getSerializableExtra("friends");
        //Toast.makeText(this, String.valueOf(friends.size()), Toast.LENGTH_SHORT).show();
        for(Person person : friends){
            if(person.getId().equals(curUser.getEmail().split("@")[0])) continue;
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

        add.setOnClickListener(new View.OnClickListener() {
            String members = new String("");
            String name = new String("");
            int count = 0;
            @Override
            public void onClick(View view) {
                for (Person person : adapter.addFriendItem){
                    if(person.getSelected() == true){
                        name = name + "," + person.getName();
                        members =members + "," + person.getId();
                        count++;
                    }
                }

                if (count < 0){
                    Toast.makeText(AddGroupActivity.this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    return;
                }
                members = friends.get(0).getId() + members;
                name = friends.get(0).getName() + name;
                String lastchat = new String("????????? ?????????!!");
                String lasttime = String.valueOf(System.currentTimeMillis());
                Chat chat = new Chat(name, lastchat, members, lasttime);
                chat.create();
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
        setSupportActionBar (toolbar); //??????????????? ??????(App Bar)??? ??????
        ActionBar actionBar = getSupportActionBar(); //?????? ????????? ?????? ?????? ?????????
        actionBar.setDisplayHomeAsUpEnabled (true); // ????????? ???????????? ?????? ?????????
        */
    }

    //??? ????????? ?????? ?????? ????????? ???????????? ??????????????? ??????
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup, R.anim.todown);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home: //?????? ?????????????????? ????????? ??? ??????
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }
}