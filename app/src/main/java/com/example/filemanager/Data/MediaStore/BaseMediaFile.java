package com.example.filemanager.Data.MediaStore;

import android.net.Uri;

public abstract class BaseMediaFile
{
    protected  Uri uri;

    protected String name;

    protected String MIMEType;


    public BaseMediaFile(Uri url, String name, String MIMEType) {
        this.uri = url;
        this.name = name;
        this.MIMEType = MIMEType;
    }

    public void setMIMEType(String type){
        this.MIMEType = type;
    }

    public Uri getUri() {
        return uri;
    }

    public BaseMediaFile(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getMIMEType() {
        return MIMEType;
    }
}