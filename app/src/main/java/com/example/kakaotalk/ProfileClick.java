package com.example.kakaotalk;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileClick extends AppCompatActivity {

    ImageButton backbtn, chatbtn, settingbtn;
    TextView nametext, messagetext, btntext;
    RelativeLayout backgroundImg;
    CircleImageView profileimg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_click);

        Intent intent = getIntent();
        String name = getString(R.string.name).format(intent.getStringExtra("이름"));
        String message = getString(R.string.message).format(intent.getStringExtra("메세지"));
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)profileimg.getDrawable()).getBitmap();
                float scale = (float) (1024/(float)bitmap.getWidth());
                int image_w = (int) (bitmap.getWidth() * scale);
                int image_h = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)backgroundImg.getBackground()).getBitmap();
                float scale = (float) (1024/(float)bitmap.getWidth());
                int image_w = (int) (bitmap.getWidth() * scale);
                int image_h = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

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
                /**
                 * 프로필 편집 코드 짜야함
                 **/
                Toast.makeText(view.getContext(), "프로필 편집 버튼클릭함", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ProfileManage.class);
                intent.putExtra("이름", name);
                intent.putExtra("메세지",message);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //애니메이션 없이 부드럽게 화면 넘어가기
                view.getContext().startActivity(intent);

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
}
