package com.example.filemanager.Data.MediaStore;

import android.app.Application;
import android.content.Context;

import com.example.filemanager.Data.MediaStore.AudioStore.AudioStore;
import com.example.filemanager.Data.MediaStore.DocumentStore.DocumentStore;
import com.example.filemanager.Data.MediaStore.ImageStore.ImageStore;
import com.example.filemanager.Data.MediaStore.VideoStore.VideoStore;

import java.util.HashMap;

/**
 * Singleton class used to provide access to data store on shared media folders </br></br>
 * (Image, Audio, Video and Documents)
 * */
public class MediaRepository {

    Context appContext;
    static MediaRepository sInstance;
    public static int AUDIO=0;
    public static int VIDEO=1;
    public static int IMAGE =2;
    public static int DOC=3;
    public static int DONWLOAD=4;
    HashMap<Integer,BaseMediaStore> mediaStoreMap;

    public static MediaRepository getInstance(Application app){
        if(sInstance==null){
            synchronized (MediaRepository.class){
                if(sInstance==null)
                    sInstance = new MediaRepository(app);
            }
        }
        return sInstance;
    }

    private MediaRepository(Application app){
        appContext = app.getApplicationContext();
        mediaStoreMap = new HashMap<>();
        mediaStoreMap.put(AUDIO, new AudioStore(appContext));
        mediaStoreMap.put(DOC, new DocumentStore(appContext));
        mediaStoreMap.put(IMAGE, new ImageStore(appContext));
        mediaStoreMap.put(VIDEO, new VideoStore(appContext));
    }

    public BaseMediaStore getMediaStore (int mediaStoreType){

        return mediaStoreMap.get(mediaStoreType);
    }

}
