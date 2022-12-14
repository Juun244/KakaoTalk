package com.example.kakaotalk;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements Filterable {


    /* ----------------------------------------------------------------------------*/
    // extends 할 때 필요한 메서드 구현
    public ArrayList<Person> items = new ArrayList<Person>();
    public ArrayList<Person> filteredList = new ArrayList<Person>();    //검색 액티비티에서 사용되는 array
    ArrayList<Person> restoreItems = new ArrayList<Person>();

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
        Log.d("PersonAdapter", item.getEmail());
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
                    String id = item.getEmail().substring(0, item.getEmail().indexOf("@"));
                    Intent intent = new Intent(v.getContext(), ProfileClick.class);
                    //myProfileActivity에 누른 아이템의 이름과 메세지를 넘겨줌
                    intent.putExtra("이름", text);
                    intent.putExtra("메세지",text2);
                    intent.putExtra("이메일", id);
                    Log.d("전달1", item.getEmail());
                    /*
                     * 내 프로필을 열었을 경우 1:1대화 버튼 대신
                     * 프로필 편집 버튼을 활성화 시키기 위한 변수
                     */
                    intent.putExtra("내프로필",true);
                    //새 창이 위로 올라오는 애니메이션
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                    v.getContext().startActivity(intent,activityOptions.toBundle());
                }
                else if(pos != RecyclerView.NO_POSITION && pos != 0) {
                    //Toast.makeText(v.getContext(), "타인의 프로필을 눌렀습니다.", Toast.LENGTH_SHORT).show();

                    String text = viewHolder.textView.getText().toString();
                    String text2 = viewHolder.textView2.getText().toString();
                    String id = item.getEmail().substring(0, item.getEmail().indexOf("@"));
                    Intent intent = new Intent(v.getContext(),
                            ProfileClick.class);
                    //myProfileActivity에 누른 아이템의 이름과 메세지를 넘겨줌
                    intent.putExtra("이름", text);
                    intent.putExtra("메세지",text2);
                    intent.putExtra("이메일", id);
                    Log.d("전달2", item.getEmail());
                    /*
                    * 타인의 프로필을 열었을 경우 프로필 편집버튼 대신
                    * 1:1대화 버튼을 활성화 시키기 위한 변수
                    */
                    intent.putExtra("내프로필",false);

                    //새 창이 위로 올라오는 애니메이션
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.fromdown,R.anim.toup);
                    v.getContext().startActivity(intent,activityOptions.toBundle());
                }
            }
        });
    }

    /* 데이터 필터 검색 Filterable implement
    * ChatSearchActivity.java에서 사용되는 Filter를 생성하기 위한 코드
    */
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            //Automatic on background thread
            String filterPattern;
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredList.clear();
                filterPattern = constraint.toString().toLowerCase().trim();
                Log.d("문자열 길이", String.valueOf(filterPattern.length()));

                if(filterPattern.length() != 0) {
                    for (Person item : restoreItems) {
                        //TODO filter 대상 setting
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            //Automatic on UI thread
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (filterPattern == null || filterPattern.length() == 0) {
                    items.clear();
                    items.addAll(restoreItems);
                    Log.d("아이템 목록 : ", items.toString());
                }else{
                    items.clear();
                    items.addAll((ArrayList) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    /* ----------------------------------------------------------------------------*/

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        ImageView imageView;

        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getText());

            userDB.child(item.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Person person = snapshot.getValue(Person.class);
                    if(person.getImage() == null || person.getImage().equals("")) {
                        imageView.setImageResource(R.drawable.default_profile);
                    } else {
                        Glide.with(itemView).load(person.getImage()).into(imageView);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    public void addItem(Person item) {
        items.add(item);
    }
    public void addRestore(Person item){
        restoreItems.add(item);
    }
    public void addFirst(Person item){
        items.add(0, item);
    }
    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }
    public Person getItem(int position) { return items.get(position); }
    public void setItem(int position, Person item) {
        items.set(position, item);
    }
}