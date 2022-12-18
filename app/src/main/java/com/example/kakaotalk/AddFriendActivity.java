package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriendActivity extends AppCompatActivity {

    private MainActivity mainActivity;

    public ArrayList<Person> friends;

    EditText Text1;

    TextView name;
    CircleImageView profile_img;
    Button add;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser curUser = mAuth.getCurrentUser();

    DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");

    private Person me;
    Person target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_friend);

        profile_img = (CircleImageView) findViewById(R.id.profile_img);
        name = (TextView) findViewById(R.id.profile_text);
        add = (Button) findViewById(R.id.add_profile_btn);


        Intent intent = getIntent();
        //friends = mainActivity.loadfriends();
        friends = (ArrayList<Person>) intent.getSerializableExtra("people");
        TextView myid = (TextView) findViewById(R.id.searchTextItem2);
        //Toast.makeText(getApplicationContext(), String.valueOf(friends.size()), Toast.LENGTH_SHORT).show();
        myid.setText(curUser.getEmail().split("@")[0]);

        Text1 = (EditText)findViewById(R.id.find_id);
        final String TEXT = Text1.getText().toString();

        final LinearLayout idLayout = (LinearLayout) findViewById(R.id.id_layout);
        final RelativeLayout profileLayout = (RelativeLayout) findViewById(R.id.profile_layout);

        ImageButton backBtn = (ImageButton) findViewById(R.id.search_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me = friends.get(0);
                me.setFriendList(me.getFriendList()+","+target.getId());
                userDB.child(me.getId()).setValue(me);
                finish();
            }
        });

        Text1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                try {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            // 친구 아이디 입력 시 넘어갈 xml과 연결하는 코드
                            // 넘길 데이터: 프로필 이미지, 아이디
                            for (Person person : friends) {
                                if (person.getEmail().substring(0, person.getEmail().indexOf("@")).equals(Text1.getText().toString())) {
                                    //if  break;
                                    name.setText(person.getName());
                                    if (person.getImage() == null || person.getImage().equals("")) {
                                        profile_img.setImageResource(R.drawable.default_profile);
                                    } else {
                                        Glide.with(profile_img).load(person.getImage()).into(profile_img);
                                    }
                                    //Glide.with(profile_img).load(person.getImage()).into(profile_img);
                                    idLayout.setVisibility(View.GONE);
                                    profileLayout.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT).show();
                                    target = person;
                                    return true;
                                }
                                idLayout.setVisibility(View.VISIBLE);
                                profileLayout.setVisibility(View.INVISIBLE);
                            }
                            Text1.setText(null);
                    }
                }catch (Exception e) {}
                return false;
            }
        });

        /*
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar(); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
        */
    }


    //창 종료시 현재 창이 아래로 내려가는 애니메이션 추가
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.fromup, R.anim.todown);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home: //툴바 뒤로가기버튼 눌렸을 때 동작
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }
}