package com.example.kesha.weddingcard1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Camera;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class ScanActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    ImageView im;
    Camera camera;
    private CameraManager mCameraManager;
    private boolean isFlashOn;
    private boolean hasFlash;
    android.hardware.Camera.Parameters params;
    private MediaPlayer mp;
    int val = 0;
    Context c;
    private String mCameraId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        c = this;
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        im = (ImageView) findViewById(R.id.flash);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (val == 0) {
                    hasFlash = getApplicationContext().getPackageManager()
                            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    if (!hasFlash) {
                        Toast.makeText(getApplicationContext(), "Flash light not found", Toast.LENGTH_SHORT).show();
                    }

                    try {

                            mCameraManager.setTorchMode(mCameraId, true);

                            im.setImageResource(R.mipmap.flashon);
                            val = 1;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

/*
                    try {
                        camera = Camera;
                        params = camera.getParameters();
                        params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(params);

                         }
                    catch (RuntimeException e)
                    {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    camera.startPreview();*/
                im.setImageResource(R.mipmap.flashon);
                    val = 1;
                }
                else {

                    try {

                            mCameraManager.setTorchMode(mCameraId, false);
                           // playOnOffSound();
                            val=0;
                            im.setImageResource(R.mipmap.flashoff);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });


        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if(!barcode.isOperational())
        {
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                try
                {
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    {

                        cameraSource.start(cameraView.getHolder());

                       // params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_ON);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections)
            {
                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
                if(barcodes.size() > 0)
                {
                    Intent intent = new Intent();
                    intent.putExtra("barcode",  barcodes.valueAt(0));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

  /*  private void playOnOffSound(){

        mp = MediaPlayer.create(ScanActivity.this, R.mipmap);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }
*/


}
