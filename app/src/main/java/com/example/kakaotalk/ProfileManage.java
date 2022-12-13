package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileManage extends AppCompatActivity {
    ImageButton back_btn, set_image_btn, set_name_btn, set_state_btn;
    Button set_backgr_btn, save_btn;
    CircleImageView profile_img;
    TextView name_text, state_text;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manage);
        Intent intent = getIntent();

        String name = getString(R.string.name).format(intent.getStringExtra("이름"));
        String message = getString(R.string.message).format(intent.getStringExtra("메세지"));

        back_btn = findViewById(R.id.back_btn);
        set_image_btn = findViewById(R.id.set_image_btn);
        set_name_btn = findViewById(R.id.set_name_btn);
        set_state_btn = findViewById(R.id.set_state_btn);
        set_backgr_btn = findViewById(R.id.set_backgr_btn);
        save_btn = findViewById(R.id.save_btn);
        profile_img = findViewById(R.id.profile_img); //이미지뷰입니다.
        name_text = findViewById(R.id.name_text);
        state_text = findViewById(R.id.state_text);

        name_text.setText(name);
        state_text.setText(message);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "뒤로가기를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        set_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "프로필 이미지 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        set_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "이름 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        set_state_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "상태메세지 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        set_backgr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "배경화면 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "적용 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });


    }
    //애니메이션을 없애 종료 시 프로필 화면으로의 전환을 부드럽게 함.
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
