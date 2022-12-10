package com.example.kakaotalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class chat extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.chat,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.chat_recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ChatAdapter adapter = new ChatAdapter();
        adapter.addItem(new Person("홍길동", "...", "MM/DD"));
        adapter.addItem(new Person("김영희", "프로필 뭐하지", "MM/DD"));
        adapter.addItem(new Person("김철수", "ㅎㅇ", "MM/DD"));
        adapter.addItem(new Person("김민지", "정산 부탁드립니다.", "MM/DD"));
        adapter.addItem(new Person("홍길동", "여행 언제가지", "MM/DD"));
        adapter.addItem(new Person("김영희", "안녕", "MM/DD"));
        adapter.addItem(new Person("김철수", "10시 30분", "MM/DD"));
        adapter.addItem(new Person("김민지", "뭐함?", "MM/DD"));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
