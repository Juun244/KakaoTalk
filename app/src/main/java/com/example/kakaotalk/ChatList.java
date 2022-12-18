package com.example.kakaotalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class ChatList extends Fragment {
    private DatabaseReference chatDB =  FirebaseDatabase.getInstance().getReference("Chats");
    private DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
    private View view;

    ChatAdapter adapter = new ChatAdapter();
    public RecyclerView recyclerView;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser me = mAuth.getCurrentUser();

    ArrayList<Chat> chats = new ArrayList<Chat>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.chat_list,container,false);
        recyclerView = view.findViewById(R.id.chat_recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        readChat();
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void readChat(){
        //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
        chatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.chatItem.clear();
                chats.clear();
                Chat chat;
                String curTime = String.valueOf(System.currentTimeMillis());
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    chat = dataSnapshot.getValue(Chat.class);
                    String[] str = chat.getMembers().split(",");
                    for(String tmp : str){
                        String myid = me.getEmail().substring(0, me.getEmail().indexOf("@"));
                        if(myid.equals(tmp)) adapter.addItem(chat);
                    }
                    chats.add(chat);
                    try {
                        chat.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
