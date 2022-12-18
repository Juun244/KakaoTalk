package com.example.kakaotalk;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AddGroupAdapter extends RecyclerView.Adapter<AddGroupAdapter.ViewHolder> {

    public ArrayList<Person> addFriendItem = new ArrayList<Person>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.add_group_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person item = addFriendItem.get(position);
        viewHolder.setItem(item);
        //RecyclerView 아이템 클릭 이벤트
        viewHolder.selected.setOnCheckedChangeListener(null);
        viewHolder.selected.setChecked(item.getSelected());
        viewHolder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setSelected(item.isSelected);
                Toast.makeText(compoundButton.getContext(), "selected", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addFriendItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView textView;
        CheckBox selected;

        public ViewHolder(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.addFriendItem_imageview);
            textView = itemView.findViewById(R.id.addFriendItem_textview);
            selected = itemView.findViewById(R.id.selected);
        }

        public void setItem(Person item) {

            textView.setText(item.getName());

            if(item.getImage() == null || item.getImage().equals("")) {
                imgView.setImageResource(R.drawable.default_profile);
            } else {
                Glide.with(itemView).load(item.getImage()).into(imgView);
            }
        }
    }

    public void addItem(Person item) {
        addFriendItem.add(item);
    }

    public void setItems(ArrayList<Person> chatItem) {
        this.addFriendItem = chatItem;
    }

    public Person getItem(int position) {
        return addFriendItem.get(position);
    }
    public void setItem(int position, Person item) {
        addFriendItem.set(position, item);
    }
}
