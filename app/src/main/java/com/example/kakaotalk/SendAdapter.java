package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SendAdapter extends RecyclerView.Adapter<SendAdapter.ViewHolder> {

    private ArrayList<MessageObject> dataset = new ArrayList<MessageObject>();

    private HashMap<String, Integer> e_hash = new HashMap<String, Integer>();

    @NonNull
    @Override
    public SendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getViewSrc(viewType), parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull SendAdapter.ViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private int viewType;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
        }

        public void bind(MessageObject item){
            e_hash.put("1", R.drawable.open_profile_ryan01); e_hash.put("2", R.drawable.open_profile_ryan02); e_hash.put("3", R.drawable.open_profile_ryan03); e_hash.put("4", R.drawable.open_profile_ryan04); e_hash.put("5", R.drawable.open_profile_ryan05);
            e_hash.put("6", R.drawable.open_profile_ryan06); e_hash.put("7", R.drawable.open_profile_ryan07); e_hash.put("8", R.drawable.open_profile_ryan08); e_hash.put("9", R.drawable.open_profile_ryan09);

            if(viewType == 0){
                bindSentMessage(item);
            }
            else if(viewType == 1){
                bindReceivedMessage(item);
            }
        }

        private void bindSentMessage(MessageObject item){
            TextView myMessage = itemView.findViewById(R.id.my_chat);
            TextView myTime = itemView.findViewById(R.id.my_chatItem_time);


            String[] tmp = item.message.split("_");
            if(tmp[0].equals("emoticon")){
                if(tmp[1].equals("ryan")){
                    if(e_hash.containsKey(tmp[2])){
                        myTime.setText(item.getDate());
                        myMessage.setText("");
                        myMessage.setBackgroundResource(e_hash.get(tmp[2]));
                        return;
                    }
                }
            }
            myTime.setText(item.getDate());
            myMessage.setText(item.message);
        }

        private void bindReceivedMessage(MessageObject item){
            TextView otherMessage = itemView.findViewById(R.id.others_chat);
            TextView otherTime = itemView.findViewById(R.id.other_chatItem_time);

            TextView user_name = itemView.findViewById(R.id.chatItem_name);
            ImageView profile = itemView.findViewById(R.id.chatItem_image);

            String[] tmp = item.message.split("_");
            if(tmp[0].equals("emoticon")){
                if(tmp[1].equals("ryan")){
                    if(e_hash.containsKey(tmp[2])){
                        otherTime.setText(item.getDate());
                        otherMessage.setText("");
                        otherMessage.setBackgroundResource(e_hash.get(tmp[2]));
                        return;
                    }
                }
            }
            otherTime.setText(item.getDate());
            otherMessage.setText(item.message);

            DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
            userDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Person person = snapshot.child(item.userId).getValue(Person.class);
                    user_name.setText(person.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            profile.setImageResource(R.drawable.default_profile);

        }
    }
    public void submitData(ArrayList<MessageObject> newData){
        dataset = newData;
        notifyDataSetChanged();
    }

    private int getViewSrc(int viewType){
        if(viewType == 0){
            return R.layout.my_message_item;
        }
        else if(viewType == 1){
            return R.layout.others_message_item;
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position){
        return dataset.get(position).getViewType();
    }
}
