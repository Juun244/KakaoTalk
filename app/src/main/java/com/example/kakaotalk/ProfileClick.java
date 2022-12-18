package com.example.kakaotalk;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
<<<<<<< Updated upstream
        import android.view.View;
=======
import android.util.Log;
import android.view.View;
>>>>>>> Stashed changes
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

<<<<<<< Updated upstream
        import androidx.annotation.Nullable;
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
>>>>>>> Stashed changes
        import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
<<<<<<< Updated upstream
=======
import java.text.SimpleDateFormat;
>>>>>>> Stashed changes

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileClick extends AppCompatActivity {

    ImageButton backbtn, chatbtn, settingbtn;
    TextView nametext, messagetext, btntext;
    RelativeLayout backgroundImg;
    CircleImageView profileimg;
<<<<<<< Updated upstream
=======

    // profileimg에 넣어줄 사진을 가져오기 위해 필요한 변수
    DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = mAuth.getCurrentUser();
    DatabaseReference chatDB = FirebaseDatabase.getInstance().getReference("Chats");

    Person person;

    String other;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_click);

        Intent intent = getIntent();
        String name = getString(R.string.name).format(intent.getStringExtra("이름"));
        String message = getString(R.string.message).format(intent.getStringExtra("메세지"));
<<<<<<< Updated upstream
=======
        String email = intent.getStringExtra("이메일");
        Log.d("ProfileClick", email);
>>>>>>> Stashed changes
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

        // 현재 프로필을 누른 사람의 이미지를 설정

        userDB.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                person = snapshot.getValue(Person.class);
                if(person.getImage() == null || person.getImage().equals("")) {
                    profileimg.setImageResource(R.drawable.default_profile);
                } else {
                    Glide.with(profileimg).load(person.getImage()).into(profileimg);
                }
                other = person.getEmail().substring(0, person.getEmail().indexOf("@"));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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
                intent.putExtra("이메일",email);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); //애니메이션 없이 부드럽게 화면 넘어가기
                view.getContext().startActivity(intent);
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 1:1 채팅 버튼 누르면 1:1채팅 화면으로 넘어가는 코드 짜야함
                 **/
                //String in = person.getEmail().substring(0, person.getImage().indexOf("@"));
                String lastchat = new String("새로운 대화방!!");
                String members = curUser.getEmail().substring(0, curUser.getEmail().indexOf("@")) + "," + other;
                String lasttime = String.valueOf(System.currentTimeMillis());
                Chat chat = new Chat(name, lastchat, members, lasttime);
                chat.create();
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
