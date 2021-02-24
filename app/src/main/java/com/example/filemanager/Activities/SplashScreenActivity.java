package com.example.filemanager.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.filemanager.R;
public class SplashScreenActivity extends AppCompatActivity {
    public static final int REQUEST_READ_EXTERNAL_STORAGE =100;
    private static final String TAG = "SplashScreenActivity";
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        handler = new Handler();
        requestStoragePermissions();

    }

    public void launchHomeActivity(){
        Intent i = new Intent(this,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finish();
    }

    public void loadSplashScreen(){
        int waitTime_ms = 1500;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchHomeActivity(); //launch after wait time is elapsed
            }
        },waitTime_ms);
    }

    public void requestStoragePermissions(){
        Log.e(TAG, "requestStoragePermissions: ");

        boolean readPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;

        if(readPermissionGranted) {
            loadSplashScreen(); //permission already granted, no need to ask user for permission
            return;
        }

         ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

         if( requestCode== REQUEST_READ_EXTERNAL_STORAGE){
             boolean readPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                     == PackageManager.PERMISSION_GRANTED;
             if(readPermissionGranted)
                 loadSplashScreen();

             else{
                 //Request again
                 final Activity activityContext = this;
                 new AlertDialog.Builder(activityContext)
                         .setTitle("Request Storage Permission")
                         .setMessage("This app needs access to External Storage so it can display directories and files as needed. Grant permission?")
                         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                                 finish();
                             }
                         })
                         .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 ActivityCompat.requestPermissions(activityContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_READ_EXTERNAL_STORAGE);
                             }
                         }).create().show();
             }
         }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
