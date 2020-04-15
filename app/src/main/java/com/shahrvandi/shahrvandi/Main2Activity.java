package com.shahrvandi.shahrvandi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class Main2Activity extends AppCompatActivity {
    public static final int REQUEST_TAKE_GALLERY_VIDEO = 1111;
    Button button;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.button);
        videoView=findViewById(R.id.videoView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoIntent=new Intent(Intent.ACTION_PICK);
                videoIntent.setType("video/*");
                videoIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(videoIntent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TAKE_GALLERY_VIDEO){
            Uri selectedImageUri = data.getData();
            videoView.setVideoURI(selectedImageUri);
            videoView.start();
            Log.d("selectedImageUri",selectedImageUri.toString());

            // OI FILE Manager
            String filemanagerstring = selectedImageUri.getPath();

            // MEDIA GALLERY
//            String selectedImagePath = data.getPath(selectedImageUri);
//            if (selectedImagePath != null) {
//                Log.d("selectedImagePath",selectedImagePath+" ");
//                    textView.setText(selectedImagePath);
                Intent intent = new Intent(Main2Activity.this,
                        Main2Activity.class);
//                intent.putExtra("path", selectedImagePath);
                startActivity(intent);
//            }
        }
    }
}
