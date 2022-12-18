package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends Fragment {

    private View view;
    private TextView name_textView, telephone_textview, account_textview, theme_textview;
    private ImageView myProfileImg;
    private String themeRes;

    protected DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");
    protected FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.settings,container,false);
        Button btn_manage =(Button) view.findViewById(R.id.btn_manage);
        Button btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        name_textView = view.findViewById(R.id.name_textview);
        telephone_textview = view.findViewById(R.id.telephone_textview);
        myProfileImg = view.findViewById(R.id.chatItem_imageview);

        account_textview = (TextView) view.findViewById(R.id.chatItem_textview_sub1);
        theme_textview = (TextView) view.findViewById(R.id.chatItem_textview_sub2);

        account_textview.setText(me.getEmail());

        themeRes = getContext().getTheme().toString();
        themeRes = themeRes.substring(themeRes.indexOf("/")+1, themeRes.indexOf(",")+1);
        theme_textview.setText(themeRes);

            userDB.child(me.getEmail().substring(0, me.getEmail().indexOf("@"))).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Person person = snapshot.getValue(Person.class);
                    try {
                        if (person.getImage() == null || person.getImage().equals("")) {
                            myProfileImg.setImageResource(R.drawable.default_profile);
                        } else {
                            Glide.with(view).load(person.getImage()).into(myProfileImg);
                        }
                        name_textView.setText(person.getName());
                        telephone_textview.setText(person.getText());
                    }
                    catch (NullPointerException e) {}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        RelativeLayout layout1 = (RelativeLayout) view.findViewById(R.id.setLayout1);
        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.setLayout2);
        LinearLayout layout3 = (LinearLayout) view.findViewById(R.id.setLayout3);
        LinearLayout layout4 = (LinearLayout) view.findViewById(R.id.setLayout4);
        RelativeLayout layout5 = (RelativeLayout) view.findViewById(R.id.setLayout5);
        LinearLayout layout6 = (LinearLayout) view.findViewById(R.id.setLayout6);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setAccountActivity.class);
                intent.putExtra("이메일", me.getEmail().substring(0, me.getEmail().indexOf("@")));
                startActivity(intent);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"개인/보안 TAB을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"친구 TAB을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"알림 TAB을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"테마 설정 TAB을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"기타 설정 TAB을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogOut.setOnClickListener(new Button.OnClickListener() { // "로그아웃" 버튼을 눌렀을 때 작동하는 OnclickListner
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                Toast.makeText(activity,"로그아웃 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();

                getActivity().finish();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                //로그아웃하면 다시 메인 창으로 가게 해야함

            }
        });

        btn_manage.setOnClickListener(new Button.OnClickListener() { //"프로필 관리" 버튼을 눌렀을 때 작동하는 OnclickListner
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                Toast.makeText(activity,"프로필 관리 버튼을 눌렀습니다", Toast.LENGTH_SHORT).show();
                String text = name_textView.getText().toString();
                String text2 = telephone_textview.getText().toString();
                Intent intent = new Intent(view.getContext(), ProfileManage.class);
                intent.putExtra("이름", text);
                intent.putExtra("메세지",text2);
                intent.putExtra("이메일", me.getEmail().substring(0, me.getEmail().indexOf("@")));
                view.getContext().startActivity(intent);
                //프로필 관리하는 xml파일로 가야함

            }
        });

        return view;
    }

}
