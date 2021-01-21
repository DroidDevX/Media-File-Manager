package com.example.filemanager.Data.MediaStore.AudioStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

public class AudioFileBase extends BaseMediaFile {


    public int size;

    public AudioFileBase(Uri url, String name, int size, String MIMEtype) {
        super(url, name,MIMEtype);
        this.size = size;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "AudioFileBase{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", url=" + uri +
                ", name='" + name + '\'' +
                ", MIMEType='" + MIMEType + '\'' +
                '}';
    }
}
