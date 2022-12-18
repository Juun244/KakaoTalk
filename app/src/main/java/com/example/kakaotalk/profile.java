package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class profile extends Fragment{ //MainActivity.java에서 setfrag(0)일 때 호출된다.
    public DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
    PersonAdapter adapter = new PersonAdapter(); //personAdapter 객체 호출
    private View view;
    public RecyclerView recyclerView;
    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.profile, container, false);
        recyclerView = view.findViewById(R.id.recyclerView); //리사이클러뷰 객체 생성
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser me = mAuth.getCurrentUser();
        // 현재 로그인한 유저의 정보부터 add
        readPerson();
        /*
        //테스트용 임시 데이터
        adapter.addItem(new Person("a@aa.com","홍길동", "프로필"));
        adapter.addItem(new Person("a@aa.com","김영희", "안녕"));
        adapter.addItem(new Person("a@aa.com","김철수", "--"));
        adapter.addItem(new Person("a@aa.com","김민지", "상태메세지입니다."));
        adapter.addItem(new Person("a@aa.com","홍길동", "프로필"));
        adapter.addItem(new Person("a@aa.com","김영희", "안녕"));
        adapter.addItem(new Person("a@aa.com","김철수", "--"));
        adapter.addItem(new Person("a@aa.com","김민지", "상태메세지입니다."));*/
        recyclerView.setAdapter(adapter);
        return view;
    }

    // userDB로 부터 id를 통해서 해당 Person 객체를 찾아주는 함수
    public void readPerson() {
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    adapter.items.clear();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser curUser = mAuth.getCurrentUser();

                    String myid = curUser.getEmail().substring(0, curUser.getEmail().indexOf("@"));

                    Person me = snapshot.child(myid).getValue(Person.class);
                    String[] str = me.getFriendList().split(",");
                    //adapter.addFirst(me);
                    for(String tmp : str){
                        Person person = snapshot.child(tmp).getValue(Person.class);
                        adapter.addItem(person);
                    }
                    recyclerView.setAdapter(adapter);
                }
                catch (NullPointerException e) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
