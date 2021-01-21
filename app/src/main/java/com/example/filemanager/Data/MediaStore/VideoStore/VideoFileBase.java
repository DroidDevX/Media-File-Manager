package com.example.filemanager.Data.MediaStore.VideoStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

public class VideoFileBase extends BaseMediaFile {


    int size;

    public VideoFileBase(Uri url, String name, int size,String MIMEType) {
        super(url, name,MIMEType);
        this.size = size;
    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Uri getUri() {
        return super.getUri();
    }

    @Override
    public String toString() {
        return "VideoFileBase{" +
                "size=" + size +
                ", url=" + uri +
                ", name='" + name + '\'' +
                ", MIMEType='" + MIMEType + '\'' +
                '}';
    }
}
