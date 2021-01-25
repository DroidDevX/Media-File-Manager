package com.example.filemanager.Data.MediaStore.VideoStore;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

import java.util.Date;

public class VideoFileBase extends BaseMediaFile {




    public VideoFileBase(Uri url, String name, int size,String MIMEType, Date dateModified) {
        super(url, name,MIMEType, dateModified,size);
    }





    @Override
    public Uri getUri() {
        return super.getUri();
    }

    @Override
    public String toString() {
         return super.toString() + "/n"+
                 "Size: "+size;

    }
}
