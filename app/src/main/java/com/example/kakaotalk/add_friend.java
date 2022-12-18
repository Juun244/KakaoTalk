package com.example.kakaotalk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class add_friend extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_add_group,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.addFriendProfile_recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);



        AddGroupAdapter adapter = new AddGroupAdapter();
        adapter.addItem(new Person("홍길동"));
        adapter.addItem(new Person("김영희"));
        adapter.addItem(new Person("김철수"));
        adapter.addItem(new Person("김민지"));
        adapter.addItem(new Person("홍길동"));
        adapter.addItem(new Person("김영희"));
        adapter.addItem(new Person("김철수"));
        adapter.addItem(new Person("김민지"));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
