package com.example.kakaotalk;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
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
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileClick extends AppCompatActivity {

    ImageButton backbtn, chatbtn, settingbtn;
    TextView nametext, messagetext, btntext;
    RelativeLayout backgroundImg;
    CircleImageView profileimg;
    String name, message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_click);

        Intent intent = getIntent();
        name = intent.getStringExtra("이름");
        message = intent.getStringExtra("메세지");
        //내 프로필인지 확인하여 어떤 버튼(1:1채팅, 프로필편집)을 출력할지 결정하는 변수, 잘못된 값이 들어올 경우 타인의 프로필로 간주함.
        Boolean myProfile = intent.getBooleanExtra("내프로필", false);


        nametext = findViewById(R.id.profile_text); // 이름 텍스트
        messagetext = findViewById(R.id.profile_text2); // 상태메세지 텍스트
        btntext = findViewById(R.id.btn_text); //"프로필 편집" 글자 뷰
        backbtn = findViewById(R.id.backbtn); //뒤로가기 버튼
        chatbtn = findViewById(R.id.chatbtn); //프로필 세부 창의 1대1 채팅 이미지 버튼
        settingbtn = findViewById(R.id.setbtn); //프로필 세부 창의 프로필편집 글자 위 연필 이미지버튼
        profileimg = findViewById(R.id.profile_img); //프로필 세부 창의 프로필 이미지뷰
        backgroundImg = findViewById(R.id.background_img);  //프로필 세부 창의 배경 레이아웃


        nametext.setText(name);
        messagetext.setText(message);

        backbtn.setOnClickListener(new View.OnClickListener() { //뒤로가기 이미지 버튼을 누르면 현재 레이아웃을 종료한다.
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //내 프로필을 열 경우 프로필설정 버튼 활성화
        if(myProfile){
            settingbtn.setVisibility(View.VISIBLE);
            chatbtn.setVisibility(View.INVISIBLE);
            btntext.setText(R.string.setbtn);
        }//타인의 프로필일 경우 1:1채팅 버튼 활성화
        else{
            settingbtn.setVisibility(View.INVISIBLE);
            chatbtn.setVisibility(View.VISIBLE);
            btntext.setText(R.string.chatbtn);
        }

        profileimg.setOnClickListener(new View.OnClickListener() { //프로필 이미지를 누르면
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "프로필 이미지를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                //이미지를 intent로 전달하기 위한 코드
                Bitmap bitmap = ((BitmapDrawable)profileimg.getDrawable()).getBitmap();
                byte[] byteArray = BitmapToByteArray(bitmap);

                Intent intent = new Intent(v.getContext(), ProfileImgClick.class);
                intent.putExtra("이미지", byteArray);
                //새 창이 위로 올라오는 애니메이션
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                v.getContext().startActivity(intent,activityOptions.toBundle());
            }
        });
        backgroundImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "배경 이미지를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                //이미지를 intent로 전달하기 위한 코드
                Bitmap bitmap = ((BitmapDrawable)backgroundImg.getBackground()).getBitmap();
                byte[] byteArray = BitmapToByteArray(bitmap);
                Intent intent = new Intent(v.getContext(), ProfileImgClick.class);
                intent.putExtra("이미지", byteArray);
                //새 창이 위로 올라오는 애니메이션
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                v.getContext().startActivity(intent,activityOptions.toBundle());
            }
        });

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//프로필편집 버튼 눌렀을 때
                //Toast.makeText(view.getContext(), "프로필 편집 버튼클릭함", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ProfileManage.class);
                intent.putExtra("이름", name);
                intent.putExtra("메세지",message);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //애니메이션 없이 부드럽게 화면 넘어가기
                startActivityResult.launch(intent);
            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 1:1 채팅 버튼 누르면 1:1채팅 화면으로 넘어가는 코드 짜야함
                 **/
                Toast.makeText(view.getContext(), "1:1채팅 버튼클릭함", Toast.LENGTH_SHORT).show();
            }
        });




    }

    //현재 창이 아래로 내려가는 애니메이션
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup,R.anim.todown);
    }

    //프로필 편집화면에서 프로필화면으로 다시 돌아왔을 때 작동하는 코드
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //이름, 메세지, 배경사진, 프로필사진을 업데이트
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        name = intent.getStringExtra("이름");
                        message = intent.getStringExtra("메세지");
                        nametext.setText(name);
                        messagetext.setText(message);

                        byte[] byteArray = intent.getByteArrayExtra("배경사진");
                        Bitmap bitmap = resize(byteArray);
                        Drawable d = new BitmapDrawable(getResources(), bitmap);
                        backgroundImg.setBackground(d);

                        byteArray = intent.getByteArrayExtra("프로필사진");
                        bitmap = resize(byteArray);
                        profileimg.setImageBitmap(bitmap);

                    }
                }
            }
    );

    //화면크기에 맞게 이미지의 크기를 조절하는 함수
    //크기 조절을 안하면 메모리 부족으로 프로필 사진, 배경사진 변경이 안될줄 알았는데 해도 크기조절해도 별차이없음
    private Bitmap resize(byte[] byteArray){
        // 화면 크기 구하기
        Display display = getWindowManager().getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        // 리사이즈할 이미지 크기 구하기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        // 화면 크기에 가장 근접하는 이미지의 리스케일 사이즈를 구한다.
        float widthScale = options.outWidth / displayWidth;
        float heightScale = options.outHeight / displayHeight;
        float scale = widthScale > heightScale ? widthScale : heightScale;

        if (scale >= 8) {
            options.inSampleSize = 8;
        } else if (scale >= 4) {
            options.inSampleSize = 4;
        } else if (scale >= 2) {
            options.inSampleSize = 2;
        } else {
            options.inSampleSize = 1;
        }
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        return bitmap;
    }

    //이미지를 intent로 전달하기 위해 array로 변환하는 함수
    public static byte[] BitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //비트맵의 크기가 크면 용량이 커지면서 배경화면 바꿀 때 팅김현상 발생함
        int image_w = (int) (bitmap.getWidth());
        int image_h = (int) (bitmap.getHeight());
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
        resize.compress(Bitmap.CompressFormat.WEBP, 100, stream);
        byte[] byteArray = stream.toByteArray();
        resize.recycle();

        return byteArray;
    }

}
