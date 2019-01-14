package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class PhotoActivity extends AppCompatActivity {
    private static final int REQ_CODE_TAKE_PICTURE = 30210;
    private static final int REQ_CODE_PHOTO_GALLERY = 30211;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    public void takePhotoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQ_CODE_TAKE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQ_CODE_TAKE_PICTURE
                && resultCode == RESULT_OK) {
            Bitmap bmp = (Bitmap) intent.getExtras().get("data");
            ImageView img =  findViewById(R.id.iv_photo);
            img.setImageBitmap(bmp);
        }else if (requestCode == REQ_CODE_PHOTO_GALLERY && resultCode == RESULT_OK){
            Uri targetUri = intent.getData();   // location of photo file
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(targetUri));
                ImageView img =  findViewById(R.id.iv_photo);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException fnfe) {
                Log.wtf("onActivityResult", fnfe);
            }
        }
    }

    public void selectPhotoFromGallery(View view) {
        Intent picIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picIntent, REQ_CODE_PHOTO_GALLERY);
    }
}
