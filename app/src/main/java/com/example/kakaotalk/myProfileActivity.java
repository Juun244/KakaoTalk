package com.example.kakaotalk;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class myProfileActivity extends AppCompatActivity {

    ImageButton backbtn,chatbtn, settingbtn;
    TextView nametext, btntext;
    CircleImageView profileimg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        nametext = findViewById(R.id.profile_text);
        btntext = findViewById(R.id.btn_text);
        backbtn = findViewById(R.id.backbtn);
        chatbtn = findViewById(R.id.chatbtn);
        settingbtn = findViewById(R.id.setbtn);
        profileimg = findViewById(R.id.profile_img);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //다른 사람의 프로필을 열 경우 chat버튼 활성화
        chatbtn.setVisibility(View.INVISIBLE);

        //내 프로필을 열 경우 setting버튼 활성화
        settingbtn.setVisibility(View.VISIBLE);
        btntext.setText(R.string.setbtn);

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingbtn.getVisibility()==View.VISIBLE){
                    settingbtn.setVisibility(View.INVISIBLE);
                    chatbtn.setVisibility(View.VISIBLE);
                    btntext.setText(R.string.chatbtn);
                }
                else{
                    settingbtn.setVisibility(View.VISIBLE);
                    chatbtn.setVisibility(View.INVISIBLE);
                    btntext.setText(R.string.setbtn);
                }
            }
        });

    }

    //현재 창이 아래로 내려가는 애니메이션
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup,R.anim.todown);
    }
}
