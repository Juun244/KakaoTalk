package com.example.kakaotalk;

import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Chat {
    private String name;
    private String lastchat;
    private String image;
    private String members;
    private String lastday;
    private String id;
    public ArrayList<Person> items = new ArrayList<>();

    private DatabaseReference userDB =  FirebaseDatabase.getInstance().getReference("Users");
    private DatabaseReference chatDB =  FirebaseDatabase.getInstance().getReference("Chats");


    public Chat() {}

    public Chat(String name, String lastchat, String members, String lastday){
        this.name = name;
        this.lastchat = lastchat;
        this.members = members;
        this.lastday = lastday;
    }

    public Chat(String name, String lastchat, String members, String lastday, String id){
        this.name = name;
        this.lastchat = lastchat;
        this.members = members;
        this.lastday = lastday;
        this.id = id;
    }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getLastchat() { return lastchat; }
    public String getMembers() { return members; }
    public String getLastday() { return lastday; }
    public String getId() { return id; }

    public void setName(String name) { this.name = name; }
    public void setImage(String image) { this.image = image; }
    public void setLastchat(String lastchat) { this.lastchat = lastchat; }
    public void setLastday(String lastday) { this.lastday = lastday; }
    public void setId(String id){ this.id = id; }
    public void setMembers(String members) { this.members = members; }

    public void create(){
        this.id = "CHAT" + System.currentTimeMillis();
        DatabaseReference chat = chatDB.child(this.id);
        chat.setValue(this);
    }

    public void load() throws IOException {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CHANGUNIV";

        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }

        File file = new File(dir + "/"+ this.id +".txt");

        FileWriter writer;

        if(!file.exists()){
            file.createNewFile();
            writer = new FileWriter(file, true);
            writer.write(id + ":" + members);
            writer.flush();
            writer.close();
        }
    }

}
