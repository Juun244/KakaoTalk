package com.example.kakaotalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class profile extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.profile,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PersonAdapter adapter = new PersonAdapter();
        adapter.addItem(new Person("홍길동", "프로필"));
        adapter.addItem(new Person("김영희", "안녕"));
        adapter.addItem(new Person("김철수", "--"));
        adapter.addItem(new Person("김민지", "상태메세지입니다."));
        adapter.addItem(new Person("홍길동", "프로필"));
        adapter.addItem(new Person("김영희", "안녕"));
        adapter.addItem(new Person("김철수", "--"));
        adapter.addItem(new Person("김민지", "상태메세지입니다."));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
