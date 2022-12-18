package com.example.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private profile profile;
    private ChatList chat;
    private settings settings;

    private Toolbar toolbar;
    private ActionBar actionBar;

    public static ArrayList<Person> people = new ArrayList<Person>();
    public static ArrayList<Person> friends = new ArrayList<Person>();


    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = mAuth.getCurrentUser();

    public static DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");


    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        getSupportActionBar().setTitle("친구");

        //actionBar.setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_profile: //profile 버튼을 눌렀을 때
                                getSupportActionBar().setTitle("친구");
                                mMenu.findItem(R.id.add_friend).setVisible(true);
                                mMenu.findItem(R.id.add_group).setVisible(false);
                                setFrag(0);
                                break;
                            case R.id.action_chat: //chat 버튼을 눌렀을 때
                                getSupportActionBar().setTitle("채팅");
                                mMenu.findItem(R.id.add_friend).setVisible(false);
                                mMenu.findItem(R.id.add_group).setVisible(true);
                                setFrag(1);
                                break;
                            case R.id.action_setting: //Setting 버튼을 눌렀을 때
                                getSupportActionBar().setTitle("설정");
                                mMenu.findItem(R.id.add_friend).setVisible(false);
                                mMenu.findItem(R.id.add_group).setVisible(false);
                                setFrag(2);
                                break;
                            }
                        return true;
                        }
                    ;
                }
        );

        profile = new profile(); // profile.java 객체 가져옴
        chat = new ChatList(); //chat.java 객체 가져옴
        settings = new settings(); //settings.java 객체 가져옴
        setFrag(0);
        loadpeople(); loadfriends();
        profile.readPerson();


        /*
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        if(fragment instanceof profile){
            ((profile) fragment).getView().findViewById(R.id.my_profileButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"클릭됨",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,
                            myProfileActivity.class);
                    startActivity(intent);
                }
            });
        }*/
    }
    public ArrayList<Person> loadpeople(){
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                people.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Person friend = dataSnapshot.getValue(Person.class);
                    if(friend.getEmail().equals(curUser.getEmail()) == false) people.add(friend);
                    else{
                       people.add(0, friend);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return people;
    }

    public ArrayList<Person> loadfriends(){
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    friends.clear();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser curUser = mAuth.getCurrentUser();
                    String[] str = {};

                    String myId = curUser.getEmail().substring(0, curUser.getEmail().indexOf("@"));

                    Person me = snapshot.child(myId).getValue(Person.class);
                    str = me.getFriendList().split(",");
                    for (String tmp : str) {
                        if(tmp.equals(curUser.getEmail().substring(0, curUser.getEmail().indexOf("@")))) continue;
                        Person person = snapshot.child(tmp).getValue(Person.class);
                        friends.add(person);
                    }
                    friends.add(0, me);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return friends;
    }

    private void setFrag(int n)
    {
        fm = getSupportFragmentManager(); //fragment 관리 매니저 객체
        ft= fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.frameLayout, profile); //setFrag(0)일 때 frameLayout 자리에 profile.java 호출
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.frameLayout, chat); //setFrag(1)일 때 frameLayout 자리에 chat.java 호출
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.frameLayout, settings); //setFrag(2)일 때 frameLayout 자리에 settings.java 호출
                ft.commit();
                break;

        }
    }

    // appbar setting
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);

        mMenu = menu;

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.search:
                //Toast.makeText(getApplicationContext(), "친구 검색 버튼을 눌렸습니다.", Toast.LENGTH_SHORT).show();
                //select search item
                //새 창이 위로 올라오는 애니메이션
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fromdown,R.anim.toup);
                startActivity(new Intent(this, ChatSearchActivity.class), activityOptions.toBundle());
                //chatsearchactivity.java 호출
                return true;

            case R.id.add_friend:
                //Toast.makeText(getApplicationContext(), "친구 추가 버튼을 눌렸습니다.", Toast.LENGTH_SHORT).show();
                //select add_friend item
                loadpeople(); loadpeople();
                Intent intent2 = new Intent(this, AddFriendActivity.class);
                intent2.putExtra("people", people);
                ActivityOptions activityOption2 = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fromdown,R.anim.toup);
                startActivity(intent2, activityOption2.toBundle());
                return true;

            case R.id.add_group:
                //Toast.makeText(getApplicationContext(), "채팅방 생성 버튼을 눌렸습니다.", Toast.LENGTH_SHORT).show();
                //select add_group item
                loadfriends(); loadfriends();
                Intent intent3 = new Intent(this, AddGroupActivity.class);
                intent3.putExtra("friends", friends);
                ActivityOptions activityOption3 = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fromdown,R.anim.toup);
                startActivity(intent3, activityOption3.toBundle());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}