package com.example.kakaotalk;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class MessageObject {
    public String message;
    public String userId;
    public String sendTime;
    public String name;

    int viewType;

    public class ViewType{
        public static final int Receive = 1;
        public static final int Send = 0;
    }

    MessageObject(String message){
        String[] tmp = message.split(",");
        this.userId = tmp[0];
        this.message = tmp[1];
        this.sendTime = tmp[2];
        this.viewType = 0;
        getName();
    }

    MessageObject(String message, int viewType){
        String[] tmp = message.split(",");
        this.userId = tmp[0];
        this.message = tmp[1];
        this.sendTime = tmp[2];
        this.viewType = viewType;
        getName();
    }


    public int getViewType(){
        return viewType;
    }

    public String toString(){
        return userId + "," + message + "," + sendTime;
    }

    public String getDate(){
        long curTime = Long.parseLong(sendTime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        String lasttime = timeFormat.format(curTime);
        return lasttime;
    }

    private void getName(){
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");
        userDB.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                name = person.getName();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
