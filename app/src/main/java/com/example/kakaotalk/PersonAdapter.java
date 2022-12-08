package com.example.kakaotalk;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    /* ----------------------------------------------------------------------------*/
    // extends 할 때 필요한 메서드 구현
    ArrayList<Person> items = new ArrayList<Person>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person item = items.get(position);
        viewHolder.setItem(item);

        //RecyclerView 아이템 클릭 이벤트
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();

                //첫 번째 프로필(내 프로필)일 경우 실행할 내용
                if(pos != RecyclerView.NO_POSITION && pos == 0){
                    String text = viewHolder.textView.getText().toString();
                    String text2 = viewHolder.textView2.getText().toString();
                    Intent intent = new Intent(v.getContext(),
                            myProfileActivity.class);
                    intent.putExtra("이름", text);
                    intent.putExtra("메세지",text2);
                    //새 창이 위로 올라오는 애니메이션
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                    v.getContext().startActivity(intent,activityOptions.toBundle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    /* ----------------------------------------------------------------------------*/
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getText());
        }
    }
    public void addItem(Person item) {
        items.add(item);
    }
    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }
    public Person getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, Person item) {
        items.set(position, item);
    }
}