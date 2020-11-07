package com.example.filemanager.Data.MediaStore.VideoStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.MediaFile;

public class VideoFile extends MediaFile {

    String name;
    int size;

    public VideoFile(Uri url, String name, int size) {
        super(url);
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Uri getUrl() {
        return super.getUrl();
    }


}
