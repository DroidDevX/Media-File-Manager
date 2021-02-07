package com.example.filemanager.Data.InternalStorageRepository;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageRepository {
    private static final String TAG = "InternalStorageReposito";

    private static InternalStorageRepository sInstance;

    public static InternalStorageRepository getInstance(){
        if(sInstance==null){
            synchronized (InternalStorageRepository.class){
                if(sInstance==null)
                    sInstance = new InternalStorageRepository();
            }
        }
        return sInstance;
    }

    private InternalStorageRepository(){

    }

    public List<File> getFiles(String targetPath){
        Log.d(TAG, "getFiles: path ->"+targetPath);
        File directory = new File(targetPath);
        File[] files = directory.listFiles();
        List<File> result = new ArrayList<>();
        if(files!=null) {
            Log.d(TAG, "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                Log.d(TAG, "FileName:" + files[i].getName());
                result.add(files[i]);
            }
        }
        return result;
    }

}
