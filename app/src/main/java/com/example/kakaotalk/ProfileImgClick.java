package com.example.kakaotalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileImgClick extends AppCompatActivity {
    ImageView clickedImg;
    ImageButton backbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_click);
        clickedImg = findViewById(R.id.clicked_img);
        backbtn = findViewById(R.id.backbtn);

        Intent intent = getIntent();

        byte[] byteArray = getIntent().getByteArrayExtra("이미지");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int dpi = displayMetrics.densityDpi;

        //이미지의 최대 크기 조절
        if(bitmap.getHeight()/(dpi / 160)>600){
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), 600 * (dpi / 160) , true);
        }

        //클릭한 이미지를 이미지뷰에 저장
        clickedImg.setImageBitmap(bitmap);


        //뒤로가기 이미지 버튼을 누르면 현재 레이아웃을 종료한다.
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
