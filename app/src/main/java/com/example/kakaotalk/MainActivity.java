package com.example.kakaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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