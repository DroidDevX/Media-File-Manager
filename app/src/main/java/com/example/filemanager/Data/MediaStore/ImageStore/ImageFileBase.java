package com.example.filemanager.Data.MediaStore.ImageStore;

import android.net.Uri;

import com.example.filemanager.Data.MediaStore.BaseMediaFile;

public class ImageFileBase extends BaseMediaFile {


    public int size;

    @Override
    public Uri getUri() {
        return super.getUri();
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ImageFileBase(Uri url, String name, int size,String MIMEtype) {
        super(url, name,MIMEtype);
        this.size = size;
    }

    public ImageFileBase(Uri url, String name, String MIMEType, int size) {
        super(url, name, MIMEType);
        this.size = size;
    }

    public ImageFileBase(Uri uri, int size) {
        super(uri);
        this.size = size;
    }


    @Override
    public String toString() {
        return "ImageFileBase{" +
                "size=" + size +
                ", url=" + uri +
                ", name='" + name + '\'' +
                ", MIMEType='" + MIMEType + '\'' +
                '}';
    }
}
