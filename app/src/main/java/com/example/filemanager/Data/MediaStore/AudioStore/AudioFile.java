package com.example.filemanager.Data.MediaStore.AudioStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.MediaFile;

public class AudioFile extends MediaFile {

    public String name;
    public int size;

    @Override
    public Uri getUrl() {
        return super.getUrl();
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

    public AudioFile(Uri uri, String name, int size) {
        super(uri);
        this.name = name;
        this.size = size;
    }

    //Add audio metadata here
}
