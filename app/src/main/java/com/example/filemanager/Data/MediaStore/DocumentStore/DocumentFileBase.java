package com.example.filemanager.Data.MediaStore.DocumentStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

public class DocumentFileBase extends BaseMediaFile {

    String name;
    int size;

    public DocumentFileBase(Uri uri, String name, int size) {
        super(uri);
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
    public String toString() {
        return "DocumentFile{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
