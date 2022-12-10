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
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private profile profile;
    private chat chat;
    private settings settings;

    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        //actionBar.setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_profile:
                                getSupportActionBar().setTitle("친구");
                                setFrag(0);
                                break;
                            case R.id.action_chat:
                                getSupportActionBar().setTitle("채팅");
                                setFrag(1);
                                break;
                            case R.id.action_setting:
                                getSupportActionBar().setTitle("설정");
                                setFrag(2);
                                break;
                            }
                        return true;
                        }
                    ;
                }
        );


        profile = new profile();
        chat = new chat();
        settings = new settings();
        setFrag(0);


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

    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.frameLayout,profile);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.frameLayout,chat);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.frameLayout,settings);
                ft.commit();
                break;


        }
    }

    // appbar setting
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                //select search item
                //새 창이 위로 올라오는 애니메이션
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fromdown,R.anim.toup);
                startActivity(new Intent(this, ChatSearchActivity.class), activityOptions.toBundle());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}