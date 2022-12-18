package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatSearchActivity extends AppCompatActivity{
    public DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
    PersonAdapter searchAdapter = new PersonAdapter(); //personAdapter 객체 호출
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_search); //호출시 activity_search.xml을 띄움

        Button backBtn = (Button) findViewById(R.id.backBtn);
        SearchView search = findViewById(R.id.search_view);

        recyclerView = findViewById(R.id.searchRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser me = mAuth.getCurrentUser();
        //전체 유저 정보를 읽어서 adapter에 추가
        readPerson();
        recyclerView.setAdapter(searchAdapter);


        backBtn.setOnClickListener(new View.OnClickListener() {
            // activity_search.xml에 있는 취소 버튼이 눌렸을 때
            @Override
            public void onClick(View view) {
                finish(); //finish()함수 호출 (finish()함수 : 창 종료)
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //검색버튼 입력 시 호출
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //텍스트 입력/수정 시 호출
            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
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

    // userDB로 부터 id를 통해서 해당 Person 객체를 찾아주는 함수
    public void readPerson() {
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    searchAdapter.items.clear();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser curUser = mAuth.getCurrentUser();

                    String myid = curUser.getEmail().substring(0, curUser.getEmail().indexOf("@"));

                    Person me = snapshot.child(myid).getValue(Person.class);
                    String[] str = me.getFriendList().split(",");
                    //adapter.addFirst(me);
                    for(String tmp : str){
                        Person person = snapshot.child(tmp).getValue(Person.class);
                        searchAdapter.addItem(person);
                        searchAdapter.addRestore(person);
                    }
                    recyclerView.setAdapter(searchAdapter);
                }
                catch (NullPointerException e) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}