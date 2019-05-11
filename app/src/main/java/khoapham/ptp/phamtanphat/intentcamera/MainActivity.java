package khoapham.ptp.phamtanphat.intentcamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button btnCamera,btnGallery;
    ImageView img;
    int Request_Camera = 123;
    int Request_Gallery = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.buttonCamera);
        btnGallery = findViewById(R.id.buttonGallery);
        img = findViewById(R.id.imageview);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},Request_Camera);
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Request_Gallery);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Request_Camera){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Request_Camera);
            }
        }
        if(requestCode == Request_Gallery){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent =  new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Gallery);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Request_Camera && resultCode == RESULT_OK && data != null){
           Bitmap bitmap = (Bitmap) data.getExtras().get("data");
           img.setImageBitmap(bitmap);
        }
        if(requestCode == Request_Gallery && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
