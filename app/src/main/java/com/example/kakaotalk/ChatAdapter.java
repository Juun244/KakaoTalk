package com.example.kakaotalk;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public ArrayList<Chat> chatItem = new ArrayList<Chat>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.chat_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Chat item = chatItem.get(position);
        Log.d("ChatAdapter", item.getName());
        viewHolder.setItem(item);

        //RecyclerView 아이템 클릭 이벤트
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), Chatroom.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("members", item.getMembers());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView textView, textView2, textView3;

        public ViewHolder(View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.chatItem_imageview);
            textView = itemView.findViewById(R.id.chatItem_textview);
            textView2 = itemView.findViewById(R.id.chatItem_textview2);
            textView3 = itemView.findViewById(R.id.chatItem_textview3);
        }

        public void setItem(Chat item) {
            textView.setText(item.getName());
            textView2.setText(item.getLastchat());

            long curTime = Long.parseLong(item.getLastday());
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd");
            String lasttime = timeFormat.format(curTime);

            textView3.setText(lasttime);

        }
    }

    public void addItem(Chat item) {
        chatItem.add(item);
    }

    public void setItems(ArrayList<Chat> chatItem) {
        this.chatItem = chatItem;
    }

    public Chat getItem(int position) {
        return chatItem.get(position);
    }
    public void setItem(int position, Chat item) {
        chatItem.set(position, item);
    }
}
