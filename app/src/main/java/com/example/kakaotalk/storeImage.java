package com.example.kakaotalk;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class storeImage extends AppCompatActivity {
    Intent gellery;
    private Uri imgUri;
    ImageView target;
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public storeImage(ImageView view) {
        gellery = new Intent(Intent.ACTION_GET_CONTENT);
        gellery.setType("image/");
        activityResult.launch(gellery);
        target = view;
    }


    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imgUri = result.getData().getData();

                        target.setImageURI(imgUri);

                        upLoadToFirebase(imgUri);
                    }
                }
            }
    );


    private String getImagePathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(storeImage.this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return url;
    }

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
                        Toast.makeText(storeImage.this, "프로필 사진 업로드 완료", Toast.LENGTH_SHORT).show();
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
