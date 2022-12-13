package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settings extends Fragment {

    private View view;
    private TextView name_textView, telephone_textview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.settings,container,false);
        Button btn_manage =(Button) view.findViewById(R.id.btn_manage);
        Button btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        name_textView = view.findViewById(R.id.name_textview);
        telephone_textview = view.findViewById(R.id.telephone_textview);

        /**
         * my_name과 telephone number에 settext 해야합니다.
         *
         **/

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
                view.getContext().startActivity(intent);
                //프로필 관리하는 xml파일로 가야함

            }
        });

        return view;
    }

}
