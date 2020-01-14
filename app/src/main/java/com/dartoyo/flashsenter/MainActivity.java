package com.dartoyo.flashsenter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Boolean checkFlash;
    CameraManager cameraManager;
    ImageButton imageButton;
    ImageView imageView;
    String camID;
    int a = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFlash = getApplicationContext().getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA_FLASH);
        if (!checkFlash) {
            Toast.makeText(this, "tidak ada flash", Toast.LENGTH_SHORT).show();
        }
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                camID = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        imageView = findViewById(R.id.imageV);
        imageView.setImageResource(R.drawable.lamp_off);
        imageButton = findViewById(R.id.img_btn);
        imageButton.setImageResource(R.drawable.btn_off);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                switch (a) {
                    case 1:
                        Log.d("TAG", "onClick: case 1");
                        imageView.setImageResource(R.drawable.lamp_on);
                        imageButton.setImageResource(R.drawable.btn_on);
                        try {
                            cameraManager.setTorchMode(camID, true);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        a = 2;
                        break;
                    case 2:
                        imageButton.setImageResource(R.drawable.btn_off);
                        imageView.setImageResource(R.drawable.lamp_off);
                        a = 1;
                        Log.d("TAG", "onClick: case 2");
                        try {
                            cameraManager.setTorchMode(camID, false);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        });
    }
}
