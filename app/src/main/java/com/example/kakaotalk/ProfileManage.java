package com.example.kakaotalk;

import static com.example.kakaotalk.ProfileClick.BitmapToByteArray;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileManage extends AppCompatActivity {
    ImageButton back_btn, set_image_btn;
    Button set_backgr_btn, save_btn;
    CircleImageView profile_img;
    EditText name_text, state_text;
    RelativeLayout background_img;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manage);
        Intent intent = getIntent();
        String name = intent.getStringExtra("이름");
        String message = intent.getStringExtra("메세지");


        back_btn = findViewById(R.id.back_btn);
        set_image_btn = findViewById(R.id.set_image_btn);
        set_backgr_btn = findViewById(R.id.set_backgr_btn);
        save_btn = findViewById(R.id.save_btn);
        profile_img = findViewById(R.id.profile_img); //이미지뷰입니다.
        background_img = findViewById(R.id.background_img); //렐러티브 레이아웃입니다.
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
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType("image/*");
                startActivityForResult(gallery,1);
            }
        });
        set_backgr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "배경화면 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType("image/*");
                startActivityForResult(gallery,2);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "적용 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("이름",name_text.getText().toString());
                result.putExtra("메세지",state_text.getText().toString());
                //배경사진을 전달하는 코드
                Bitmap backimg = ((BitmapDrawable)background_img.getBackground()).getBitmap();
                byte[] backArray = BitmapToByteArray(backimg);
                result.putExtra("배경사진", backArray);
                //프로필사진을 전달하는 코드
                Bitmap profimg = ((BitmapDrawable)profile_img.getDrawable()).getBitmap();
                byte[] profArray = BitmapToByteArray(profimg);
                result.putExtra("프로필사진", profArray);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        //이름과 상태메시지에 줄바꿈을 못하게 막음
        name_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });
        state_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER)
                    return true;
                return false;
            }
        });


    }


    //애니메이션을 없애 종료 시 프로필 화면으로의 전환을 부드럽게 함.
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    //갤러리에서 받아온 파일로 프로필사진이나 배경사진을 변경하는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1: //프로필 사진을 바꿀 경우
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    profile_img.setImageURI(uri);
                }
                break;
            case 2: //배경 사진을 바꿀 경우
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    //배경으로 설정하려면 Drawable형식으로 변환해야 함
                    Bitmap backgroundBitmap = null;
                    try {
                        backgroundBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Drawable d = new BitmapDrawable(backgroundBitmap);

                    background_img.setBackground(d);

                }
                break;
        }
    }


    //프로필 편집 액티비티에서 프로필 클릭 액티비티로 돌아올 때마다 호출됨
    @Override
    public void onStop(){
        super.onStop();
        //편집된 프로필, 배경, 이름, 상태메세지를 데이터베이스에 업데이트 하는 작업 필요함
    }

}
