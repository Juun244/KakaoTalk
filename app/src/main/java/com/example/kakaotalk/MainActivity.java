package com.example.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private profile profile;
    private chat chat;
    private settings settings;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_profile:
                                setFrag(0);
                                break;
                            case R.id.action_chat:
                                setFrag(1);
                                break;
                            case R.id.action_setting:
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


        button = findViewById(R.id.my_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        myProfileActivity.class);
                //새 창이 위로 올라오는 애니메이션
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                startActivity(intent,activityOptions.toBundle());
            }
        });
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
}