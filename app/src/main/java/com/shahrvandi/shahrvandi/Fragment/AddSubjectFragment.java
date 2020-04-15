package com.shahrvandi.shahrvandi.Fragment;


import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mvc.imagepicker.ImagePicker;
import com.shahrvandi.shahrvandi.Adapter.AttachFileAdapter;
import com.shahrvandi.shahrvandi.IAddAttachment;
import com.shahrvandi.shahrvandi.Main2Activity;
import com.shahrvandi.shahrvandi.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubjectFragment extends BottomSheetDialogFragment {

    public static final String CACHED_IMG_KEY = "img_key";
    public static final int SECOND_PIC_REQ = 1313;
    public static final int GALLERY_ONLY_REQ = 1212;
    public static final int REQUEST_TAKE_GALLERY_VIDEO = 1111;

    public IAddAttachment addAttachment;

    RelativeLayout cellAdd;
    ImageView imageView;
    RecyclerView rvAttachment;
    TextView textView;
    AlertDialog alertDialog;
    VideoView videoView;
    AttachFileAdapter attachFileAdapter;
    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    public AddSubjectFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_subject, container, false);
        videoView=view.findViewById(R.id.videoView);

        cellAdd =view.findViewById(R.id.cell_add);
        textView =view.findViewById(R.id.text);
        rvAttachment =view.findViewById(R.id.rv_attachment);
        imageView=view.findViewById(R.id.image);
        cellAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked();
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String path = prefs.getString(CACHED_IMG_KEY, "");
        File cached = new File(path);
        if (cached.exists()) {
//            Picasso.with(getActivity()).load(cached).into(imageView);
        }

        initFileList();
        return view;
    }

    private void initFileList() {
//        attachFileAdapter = new AttachFileAdapter(getContext(),)
    }

    String fileManagerString;
    String selectedImagePath;

    public static int arriveRequestCode(int reqCode){

        return reqCode;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_TAKE_GALLERY_VIDEO :
                Uri selectedImageUri = data.getData();
                // OI FILE Manager
                fileManagerString = selectedImageUri.getPath();

                // MEDIA GALLERY
                selectedImagePath = getPath(selectedImageUri);
                if (selectedImagePath != null) {
                    Log.d("selectedImagePath",selectedImagePath+" ");
                    videoView.setVideoPath(selectedImagePath);

//                    videoView.start();
//                    textView.setText(selectedImagePath);
//                    Intent intent = new Intent(getActivity(),
//                            Main2Activity.class);
//                    intent.putExtra("path", selectedImagePath);
//                    startActivity(intent);
                }
                break;

            case SECOND_PIC_REQ:
                String imagePathFromResult = ImagePicker.getImagePathFromResult(getActivity(),
                        requestCode, resultCode, data);
                if (imagePathFromResult != null) {
                    String path = "file:///" + imagePathFromResult;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    prefs.edit().putString(CACHED_IMG_KEY, imagePathFromResult).apply();
//                    Picasso.with(getActivity()).load(path).into(imageView);
                }
                break;
            case GALLERY_ONLY_REQ:
                String pathFromGallery = "file:///" + ImagePicker.getImagePathFromResult(getActivity(), requestCode,
                        resultCode, data);
//                Picasso.with(getActivity()).load(pathFromGallery).into(imageViewGalleryOnly);
                break;
            default:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
        }
        InputStream is = ImagePicker.getInputStreamFromResult(getActivity(), requestCode, resultCode, data);
        if (is != null) {
            textView.setText("Got input stream!");
            try {
                is.close();
            } catch (IOException ex) {
                // ignore
            }
        } else {
            textView.setText("Failed to get input stream!");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showAlertDialogButtonClicked() {
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        LinearLayout uploadImage,uploadVideo,uploadSound;
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.activity_upload, null);
        uploadImage=customLayout.findViewById(R.id.cell_upload_image);
        uploadVideo=customLayout.findViewById(R.id.cell_upload_video);
        uploadSound=customLayout.findViewById(R.id.cell_upload_sound);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(AddSubjectFragment.this, "Select your image:");
                alertDialog.dismiss();
//                openFolder();
            }
        });
        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoIntent=new  Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                videoIntent.setType("video/*");
                videoIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(videoIntent,"Select Video"),REQUEST_TAKE_GALLERY_VIDEO);
                alertDialog.dismiss();
            }
        });
        uploadSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        customLayout.setMinimumWidth((int)(displayRectangle.width() * 1f));
        customLayout.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(customLayout);
         alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        // add a button
        builder.setPositiveButton("انتخاب", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                LinearLayout editText = customLayout.findViewById(R.id.cell_upload_video);
            }
        });
    }


    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
}
