package com.example.filemanager.Data.MediaStore;

import android.content.Context;

import java.util.List;

/**
 * Helper class that uses MediaStore API to access folders in shared
 * local storage.
 * */
public abstract class BaseMediaStore  {



    Context context;

    public BaseMediaStore(Context context){
        this.context =context;
    }

    public abstract List<MediaFile> getFiles();

    public Context getContext()
    {
        return context;
    }
}
