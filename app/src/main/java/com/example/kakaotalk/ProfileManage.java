package com.example.kakaotalk;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileManage extends AppCompatActivity {
    ImageButton back_btn, set_image_btn, set_name_btn, set_state_btn;
    Button set_backgr_btn, save_btn;
    CircleImageView profile_img;
    TextView name_text, state_text;
    EditText name_edit, state_edit;
    int i =0, j=0;

    //로컬 사진첩에서 사진을 가져올 때 사용하는 변수
    private Uri imgUri;
    // firebaseStorage에 접근할 때 필요한 변수
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference("Users");

    @SuppressLint("MissingInflatedId")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manage);
        Intent intent = getIntent();

        String name = "%s".format(intent.getStringExtra("이름"));
        String message = "%s".format(intent.getStringExtra("메세지"));
        String email = intent.getStringExtra("이메일");

        back_btn = findViewById(R.id.back_btn);
        set_image_btn = findViewById(R.id.set_image_btn);
        set_name_btn = findViewById(R.id.set_name_btn);
        set_state_btn = findViewById(R.id.set_state_btn);
        set_backgr_btn = findViewById(R.id.set_backgr_btn);
        save_btn = findViewById(R.id.save_btn);
        profile_img = findViewById(R.id.profile_img); //이미지뷰입니다.
        name_text = findViewById(R.id.name_text);
        state_text = findViewById(R.id.state_text);
        name_edit = findViewById(R.id.name_edit);
        state_edit = findViewById(R.id.state_edit);

        name_text.setText(name);
        state_text.setText(message);

        userDB.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                if (person.getImage() == null || person.getImage().equals("")) {
                    profile_img.setImageResource(R.drawable.default_profile);
                } else {
                    Glide.with(profile_img).load(person.getImage()).into(profile_img);
                }
                if(person.getImage() == null) imgUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/test-62306.appspot.com/o/default_profile.jpeg?alt=media&token=ac44e567-b1fc-4c37-9c95-f3a338103b31");
                else imgUri = Uri.parse(person.getImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "뒤로가기를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        set_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "프로필 이미지 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                Intent gellery;
                gellery = new Intent(Intent.ACTION_GET_CONTENT);
                gellery.setType("image/");
                activityResult.launch(gellery);
            }
        });

        set_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "이름 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                i = 1 - i;
                if (i == 0) {
                    set_name_btn.setImageResource(R.drawable.pencil_icon);
                    String temp = name_edit.getText().toString();
                    name_text.setText(temp);
                    name_text.setVisibility(View.VISIBLE);
                    name_edit.setVisibility(View.INVISIBLE);
                } else {
                    set_name_btn.setImageResource(R.drawable.save_icon);
                    String temp = name_text.getText().toString();
                    name_edit.setText(temp);
                    name_text.setVisibility(View.INVISIBLE);
                    name_edit.setVisibility(View.VISIBLE);

                }
            }
        });


        set_state_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "상태메세지 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                j = 1 - j;
                if (j == 0) {
                    set_state_btn.setImageResource(R.drawable.pencil_icon);
                    String temp = state_edit.getText().toString();
                    state_text.setText(temp);
                    state_text.setVisibility(View.VISIBLE);
                    state_edit.setVisibility(View.INVISIBLE);
                } else {
                    set_state_btn.setImageResource(R.drawable.save_icon);
                    String temp = state_text.getText().toString();
                    state_edit.setText(temp);
                    state_text.setVisibility(View.INVISIBLE);
                    state_edit.setVisibility(View.VISIBLE);
                }
            }
        });


        set_backgr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "배경화면 설정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    userDB.child(email).child("name").setValue(name_text.getText().toString());
                    userDB.child(email).child("text").setValue(state_text.getText().toString());
                    upLoadToFirebase(imgUri);
                    Thread.sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "프로필이 적용되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //애니메이션을 없애 종료 시 프로필 화면으로의 전환을 부드럽게 함.
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imgUri = result.getData().getData();

                        profile_img.setImageURI(imgUri);
                    }
                }
            }
    );

    private String getImagePathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(ProfileManage.this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return url;
    }
//
//    private void upLoadToFirebase(Uri uri) {
//        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//        fileRef.putFile(uri);
//        FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Users").child(me.getEmail().substring(0, me.getEmail().indexOf("@")));
//        myData.child("image").setValue(uri.toString());
//        return;
    //}

        private void upLoadToFirebase(Uri uri) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // 현재 로그인 한 유저의 정보를 가져와서
                            // 내 이메일의 @ 이전 부분을 key로 가지는 DB의 내용을 불러온다.
                            FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference myData = FirebaseDatabase.getInstance().getReference("Users").child(me.getEmail().substring(0, me.getEmail().indexOf("@")));
                            myData.child("image").setValue(uri.toString());
                            //Toast.makeText(storeImage.this, "프로필 사진 업로드 완료", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


      private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
