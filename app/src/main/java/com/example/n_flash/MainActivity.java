package com.example.n_flash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
ImageButton  imgbtn;
Boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgbtn = findViewById(R.id.off);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this,"cam permission is necessary!",Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void runFlashLight() {

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(!state){
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId =cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,
                                true);
                        state=true;
                        imgbtn.setImageResource(R.drawable.on);
                    }
                    catch (CameraAccessException e){

                    }
                }
                else
                {
                    CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId =cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,
                                false);
                        state=false;
                        imgbtn.setImageResource(R.drawable.off);
                    }
                    catch (CameraAccessException e){

                    }
                }
            }
        });
    }
}