package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chatroom extends AppCompatActivity {

    ImageButton chatroom_backBtn;
    ImageButton send;
    EditText message;
    RecyclerView chatting;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = mAuth.getCurrentUser();

    DatabaseReference chatDB = FirebaseDatabase.getInstance().getReference("Chats");
    DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");

    String lastSend, id, members;

    SendAdapter adapter = new SendAdapter();

    private String me = curUser.getEmail().substring(0, curUser.getEmail().indexOf("@"));

    ArrayList<MessageObject> items = new ArrayList<MessageObject>();
    ArrayList<MemberListner> memberListner = new ArrayList<MemberListner>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);

        Intent intent = getIntent();
        id = String.format("%s", intent.getStringExtra("id"));
        members = String.format("%s", intent.getStringExtra("members"));

        chatroom_backBtn = findViewById(R.id.chatroom_imageButton);
        chatroom_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        send = findViewById(R.id.sender);
        message = findViewById(R.id.message);

        chatting = findViewById(R.id.chatMain);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatting.setLayoutManager(manager);

        connectChat(members);
        
        //devTest();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastSend = String.valueOf(System.currentTimeMillis());
                chatDB.child(id).child("lastchat").setValue(message.getText().toString());
                chatDB.child(id).child("data").child(me).setValue(new String(me+","+message.getText().toString()+","+lastSend));
                chatDB.child(id).child("lastday").setValue(lastSend);
                //items.add(new MessageObject(me+","+message.getText().toString()+","+lastSend));
                message.setText("");
                //Toast.makeText(Chatroom.this, String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromleft,R.anim.toright);
    }

    public class MemberListner{
        private String memberId;
        private int count;
        DatabaseReference chatDB = FirebaseDatabase.getInstance().getReference("Chats");
        MemberListner(String memberId){
            this.memberId = memberId;
            count = 0;
            dataListner();
        }
        public void dataListner(){
            chatDB.child(id).child("data").child(memberId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String test = snapshot.getValue(String.class);
                    count ++;
                    if(test != null && count%2 == 0){
                        if(me.equals(memberId)) items.add(new MessageObject(test));
                        else items.add(new MessageObject(test, 1));
                        count = 0;
                        adapter.submitData(items);
                        chatting.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void connectChat(String members){
        for(String member : members.split(",")){
            MemberListner mem = new MemberListner(member);
            memberListner.add(mem);
            mem.dataListner();
        }
    }
}
